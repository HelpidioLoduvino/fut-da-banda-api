package com.example.futdabandaapi.service;

import com.example.futdabandaapi.model.Club;
import com.example.futdabandaapi.model.Invitation;
import com.example.futdabandaapi.model.Player;
import com.example.futdabandaapi.model.User;
import com.example.futdabandaapi.repository.ClubRepository;
import com.example.futdabandaapi.repository.InvitationRepository;
import com.example.futdabandaapi.repository.PlayerRepository;
import com.example.futdabandaapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final UserService userService;
    private final ClubService clubService;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;

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

    @Transactional
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

}
