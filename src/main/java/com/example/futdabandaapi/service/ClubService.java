package com.example.futdabandaapi.service;

import com.example.futdabandaapi.configuration.UploadPath;
import com.example.futdabandaapi.dto.ClubDto;
import com.example.futdabandaapi.mapper.ClubMapper;
import com.example.futdabandaapi.model.Championship;
import com.example.futdabandaapi.model.Club;
import com.example.futdabandaapi.model.Player;
import com.example.futdabandaapi.model.User;
import com.example.futdabandaapi.repository.ChampionshipRepository;
import com.example.futdabandaapi.repository.ClubRepository;
import com.example.futdabandaapi.repository.PlayerRepository;
import com.example.futdabandaapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.futdabandaapi.service.FileUploadService.getResourceResponseEntity;


@Service
@AllArgsConstructor
@Transactional
public class ClubService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final ChampionshipRepository championshipRepository;
    private final UserService userService;
    private final UploadPath uploadPath;
    private final FileUploadService fileUploadService;

    public Club add(Club club, MultipartFile file){
        try{
            club.setBan("Ativo");
            String filename = fileUploadService.generateUniqueFileName(file.getOriginalFilename());
            fileUploadService.saveFile(file, uploadPath.getClubUploadDir(), filename);
            club.setEmblem(uploadPath.getClubUploadDir() + filename);
            String email = userService.getCurrentUser();
            User user = userRepository.findUserByEmail(email);
            Long userId = user.getId();
            if(user.getUserRole().equals("PLAYER")){
                Player player = playerRepository.findById(userId).orElse(null);
                if(player != null){
                    if (clubRepository.existsByPlayersContaining(player)) {
                        throw new IllegalStateException("Usuário já criou um time e não pode criar outro.");
                    } else {
                        club.getPlayers().add(player);
                    }
                }
            }
            return clubRepository.save(club);
        }catch (Exception e){
            throw  new RuntimeException("Erro ao criar Clube" + e.getMessage());
        }

    }

    public Club findClubIfExists() {
        String email = userService.getCurrentUser();
        User user = userRepository.findUserByEmail(email);

        if(user == null){
            return null;
        }

        Player player = playerRepository.findById(user.getId()).orElse(null);

        if(player == null){
            return null;
        }

        Club club = clubRepository.findByPlayersContaining(player);

        if (club != null && club.getPlayers().stream().findFirst().map(captain -> captain.equals(user)).orElse(false)){
            return club;
        }

        return null;
    }

    public Boolean playerHasClub() {

        String email = userService.getCurrentUser();

        User user = userRepository.findUserByEmail(email);

        if(user == null){
            return false;
        }

        Player player = playerRepository.findById(user.getId()).orElse(null);
        if(player == null){
            return false;
        }

        Club club = clubRepository.findByPlayersContaining(player);
        return club != null;
    }

    public Boolean isPlayerClubCaptain(Long id){

        Club club = clubRepository.findById(id).orElse(null);

        if(club == null){
            return false;
        }
        String email = userService.getCurrentUser();
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            return false;
        }
        return club.getPlayers().stream().findFirst().map(captain-> captain.equals(user)).orElse(false);
    }

    public Page<ClubDto> getAll(Pageable pageable) {
        return clubRepository.findAll(pageable).map(ClubMapper.INSTANCE::toClubDto);
    }

    public ClubDto findById(Long id) {
        Club club = clubRepository.findById(id).orElse(null);
        return ClubMapper.INSTANCE.toClubDto(club);
    }

    public Club update(Club club, Long id) {
        Club existingClub = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found."));
        existingClub.setName(club.getName());
        existingClub.setAbv(club.getAbv());
        existingClub.setProvince(club.getProvince());
        existingClub.setGender(club.getGender());
        existingClub.setStatus(club.getStatus());
        existingClub.setDescription(club.getDescription());
        existingClub.setCompetition(club.getCompetition());
        existingClub.setCategory(club.getCategory());
        return clubRepository.save(existingClub);
    }

    public void updateEmblem(MultipartFile file, Long id) throws IOException {
        Club club = clubRepository.findById(id).orElse(null);
        if(club != null){
            fileUploadService.deleteFile(club.getEmblem());
            String filename = fileUploadService.generateUniqueFileName(file.getOriginalFilename());
            fileUploadService.saveFile(file, uploadPath.getClubUploadDir(), filename);
            club.setEmblem(uploadPath.getClubUploadDir() + filename);
            clubRepository.save(club);
        }
    }


    public Club ban(Long id) {
        Club club = clubRepository.findById(id).orElse(null);
        if(club == null){
            return null;
        }
        club.setBan("Bloqueado");
        return clubRepository.save(club);
    }

    public Club unban(Long id) {
        Club club = clubRepository.findById(id).orElse(null);
        if(club == null){
            return null;
        }
        club.setBan("Ativo");
        return clubRepository.save(club);
    }

    public Resource displayCover(Long id){
        try{
            Club club = clubRepository.findById(id).orElse(null);
            if(club != null){
                Path path = Paths.get(club.getEmblem());
                return getResourceResponseEntity(path);
            }
        }catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        return null;
    }

    public Boolean clubIsAvailableToCompete(Long championshipId){
        Championship championship = championshipRepository.findById(championshipId).orElse(null);
        if(championship != null){
            String email = userService.getCurrentUser();
            User user = userRepository.findUserByEmail(email);
            if(user != null && user.getUserRole().equals("PLAYER")){
                Player player = playerRepository.findById(user.getId()).orElse(null);
                if(player != null){
                    Club club = clubRepository.findByPlayersContaining(player);
                    return club != null && club.getPlayers().size() >= 5 &&
                            club.getPlayers().stream().findFirst()
                            .map(captain -> captain.equals(player)).orElse(false)
                            && club.getStatus().equals(championship.getType())
                            && club.getGender().equals(championship.getGender())
                            && club.getCategory().equals(championship.getCategory());
                }
            }
        }
        return false;
    }

    public List<ClubDto> getFour(){
        Pageable topFour = PageRequest.of(0, 4);
        List<Club> clubs = clubRepository.findAllByOrderByIdDesc(topFour);
        return clubs.stream()
                .map(ClubMapper.INSTANCE::toClubDto)
                .collect(Collectors.toList());
    }

}
