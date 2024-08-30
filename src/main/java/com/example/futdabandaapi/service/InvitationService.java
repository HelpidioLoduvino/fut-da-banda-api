package com.example.futdabandaapi.service;

import com.example.futdabandaapi.model.*;
import com.example.futdabandaapi.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final UserService userService;
    private final ClubService clubService;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final ChampionshipRepository championshipRepository;
    private final JoinChampionshipRepository joinChampionshipRepository;
    private final ChampionshipStatRepository championshipStatRepository;

    @Value("${invitation.message}")
    private String invitationMessage;

    @Value("${invitation.accept}")
    private String invitationAccepted;

    @Value("${invitation.reject}")
    private String invitationRejected;

    @Value("${join.message}")
    private String joinMessage;

    @Value("${join.accept}")
    private String joinAccept;

    @Value("${join.reject}")
    private String joinReject;

    @Value("${join.championship.message}")
    private String joinChampionshipMessage;

    public Invitation invite(Long playerId) {
        try{
            String email = userService.getCurrentUser();
            User user = userRepository.findUserByEmail(email);
            if (user == null) {
                return null;
            }

            Player sender = playerRepository.findById(user.getId()).orElse(null);
            if(sender == null) {
                return null;
            }

            Player recipient = playerRepository.findById(playerId).orElse(null);
            if(recipient == null) {
                return null;
            }

            Club club = clubService.findClubIfExists();

            Invitation invitation = new Invitation();
            invitation.setId(null);
            invitation.setSender(sender);
            invitation.setRecipient(recipient);
            invitation.setClub(club);
            invitation.setMessage(invitationMessage);
            invitation.setStatus("Convidado");
            invitation.setSeen(false);

            return invitationRepository.save(invitation);
        }catch (Exception e){
            throw new RuntimeException("Error inviting", e);
        }
    }

    public Invitation askToJoin(Long clubId){
        try{
            String email = userService.getCurrentUser();
            User user = userRepository.findUserByEmail(email);
            if(user == null) {
                return null;
            }

            Club club = clubRepository.findById(clubId).orElse(null);
            if(club == null) {
                return null;
            }

            Player sender = playerRepository.findById(user.getId()).orElse(null);
            if(sender == null) {
                return null;
            }

            Invitation invitation = new Invitation();
            invitation.setClub(club);
            invitation.setSender(sender);
            invitation.setMessage(joinMessage);
            invitation.setStatus("Pedido");
            invitation.setRecipient(club.getPlayers().get(0));
            invitation.setSeen(false);

            return invitationRepository.save(invitation);

        } catch (Exception e){
            throw new RuntimeException("Error asking to join club", e);
        }
    }

    public List<Invitation> getCaptainInvitations() {

        String email = userService.getCurrentUser();
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return null;
        }

        List<Invitation> invitations = invitationRepository.findAllByRecipientIdAndStatus(user.getId(), "Pedido");

        for (Invitation invitation : invitations) {
            invitation.setSeen(true);
        }

        invitationRepository.saveAll(invitations);

        return invitations;
    }


    public List<Invitation> getPlayerInvitations() {

        String email = userService.getCurrentUser();
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return null;
        }

        List<Invitation> invitations = invitationRepository.findAllByRecipientIdAndStatus(user.getId(), "Convidado");

        for (Invitation invitation : invitations) {
            invitation.setSeen(true);
        }

        invitationRepository.saveAll(invitations);

        return invitations;
    }

    public List<Invitation> getSenderInvitations() {

        String email = userService.getCurrentUser();
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return null;
        }

        List<Invitation> invitations =  invitationRepository.findAllBySenderIdAndStatusOrSenderIdAndStatus(user.getId(), "Aceite", user.getId(), "Rejeitado");
        for (Invitation invitation : invitations) {
            invitation.setSeen(true);
        }
        invitationRepository.saveAll(invitations);

        return invitations;
    }

    public Invitation acceptInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId).orElse(null);
        if(invitation == null) {
            return null;
        }
        invitation.setMessage(invitationAccepted);
        invitation.setStatus("Aceite");
        invitation.getClub().getPlayers().add(invitation.getRecipient());

        Player player = playerRepository.findById(invitation.getRecipient().getId()).orElse(null);
        if(player == null) {
            return null;
        }

        player.setAvailable("Indisponível");
        playerRepository.save(player);

        return invitationRepository.save(invitation);
    }

    public Invitation acceptPlayerPermission(Long invitationId) {
        Invitation playerInvitation = invitationRepository.findById(invitationId).orElse(null);
        if(playerInvitation == null) {
            return null;
        }
        playerInvitation.setMessage(joinAccept);
        playerInvitation.setStatus("Aceite");
        playerInvitation.getClub().getPlayers().add(playerInvitation.getSender());

        Player player = playerRepository.findById(playerInvitation.getSender().getId()).orElse(null);
        if(player == null) {
            return null;
        }

        player.setAvailable("Indisponível");
        playerRepository.save(player);

        return invitationRepository.save(playerInvitation);
    }

    public Invitation rejectInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId).orElse(null);
        if(invitation == null) {
            return null;
        }
        invitation.setMessage(invitationRejected);
        invitation.setStatus("Rejeitado");
        return invitationRepository.save(invitation);
    }

    public Invitation rejectPlayer(Long invitationId) {
        Invitation playerInvitation = invitationRepository.findById(invitationId).orElse(null);
        if(playerInvitation == null) {
            return null;
        }
        playerInvitation.setMessage(joinReject);
        playerInvitation.setStatus("Rejeitado");
        return invitationRepository.save(playerInvitation);
    }

    public List<Invitation> clubInvitations() {
        return invitationRepository.findAll();
    }

    public long unseenInvitations(){
        String email = userService.getCurrentUser();
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return 0;
        }
        return invitationRepository.countAllBySeenFalseAndRecipientId(user.getId());
    }

    public List<Invitation> playerAlreadyAskedForPermission() {

        String email = userService.getCurrentUser();
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return null;
        }

        return invitationRepository.findAllBySenderIdAndStatus(user.getId(), "Pedido");

    }

    public JoinChampionship askToJoinChampionship(Long championshipId) {
        try {
            Championship championship =  championshipRepository.findById(championshipId).orElse(null);
            if(championship != null){
                String email = userService.getCurrentUser();
                User user = userRepository.findUserByEmail(email);
                if(user != null){
                    Player player = playerRepository.findById(user.getId()).orElse(null);
                    if(player != null){
                        Club club = clubRepository.findByPlayersContaining(player);
                        if(club != null){
                            JoinChampionship joinChampionship = new JoinChampionship();
                            joinChampionship.setChampionship(championship);
                            joinChampionship.setClub(club);
                            joinChampionship.setMessage(joinChampionshipMessage);
                            joinChampionship.setStatus("Em Espera");
                            return joinChampionshipRepository.save(joinChampionship);
                        }
                    }
                }
            }
            return null;
        }catch (Exception e){
            throw new RuntimeException("Error asking to join championship", e);
        }
    }

    public List<JoinChampionship> getAllChampionshipInvitations(){
        return joinChampionshipRepository.findAllByStatus("Em Espera");
    }

    public List<JoinChampionship> clubAlreadyAskedForPermission(){

        String email = userService.getCurrentUser();
        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            return null;
        }

        Player player = playerRepository.findById(user.getId()).orElse(null);
        if(player != null){

            Club club = clubRepository.findByPlayersContaining(player);

            return joinChampionshipRepository.findAllByClubAndStatus(club, "Em Espera");
        }

        return null;

    }

    public JoinChampionship acceptClubInvitation(Long invitationId){

        JoinChampionship joinChampionship = joinChampionshipRepository.findById(invitationId).orElse(null);

        if(joinChampionship == null){
            return null;
        }

        joinChampionship.setStatus("Aceite");

        Championship championship = championshipRepository.findById(joinChampionship.getChampionship().getId()).orElse(null);

        if(championship != null){

            championship.getClubs().add(joinChampionship.getClub());
            championshipRepository.save(championship);

            ChampionshipStat championshipStat = new ChampionshipStat();
            championshipStat.setChampionship(championship);
            championshipStat.setClub(joinChampionship.getClub());
            championshipStatRepository.save(championshipStat);

            return joinChampionshipRepository.save(joinChampionship);
        }
        return null;
    }

    public JoinChampionship rejectClubInvitation(Long invitationId){
        JoinChampionship joinChampionship = joinChampionshipRepository.findById(invitationId).orElse(null);
        if(joinChampionship == null){
            return null;
        }
        joinChampionship.setStatus("Rejeitado");
        return joinChampionshipRepository.save(joinChampionship);
    }


}
