package com.example.futdabandaapi.service;

import com.example.futdabandaapi.model.Club;
import com.example.futdabandaapi.model.Player;
import com.example.futdabandaapi.model.User;
import com.example.futdabandaapi.repository.ClubRepository;
import com.example.futdabandaapi.repository.PlayerRepository;
import com.example.futdabandaapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static com.example.futdabandaapi.service.UserService.getResourceResponseEntity;

@Service
@AllArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final UserService userService;
    private final UploadPath uploadPath;

    public Club add(Club club, MultipartFile file) throws IOException {
        try{
            club.setBan("Ativo");
            String filename = generateUniqueFileName(file.getOriginalFilename());
            saveFile(file, filename);
            club.setEmblem(uploadPath.getClubUploadDir() + filename);
            club.setReadyToPlay(false);

            String email = userService.getCurrentUser();
            User user = userRepository.findUserByEmail(email);
            Long userId = user.getId();
            String userRole = user.getUserRole();

            if(userRole.equals("CAPTAIN")){
                Player player = playerRepository.findById(userId).orElse(null);
                assert player != null;
                if (clubRepository.existsByPlayersContaining(player)) {
                    throw new IllegalStateException("Usuário já criou um time e não pode criar outro.");
                } else {
                    club.getPlayers().add(player);
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
        assert user != null;
        Player player = playerRepository.findById(user.getId()).orElse(null);
        assert player != null;
        Club club = clubRepository.findByPlayersContaining(player);

        if (club != null && club.getPlayers().stream().
                anyMatch(captain->player.equals(user) && player.getUserRole().equals("CAPTAIN"))) {
            return club;
        }

        return null;
    }

    public Boolean isPlayerClubCaptain(Long id){
        Club club = clubRepository.findById(id).orElse(null);
        assert club != null;
        String email = userService.getCurrentUser();
        User user = userRepository.findUserByEmail(email);
        assert user != null;
        return club.getPlayers().stream().
                anyMatch(player->player.equals(user) && player.getUserRole().equals("CAPTAIN"));
    }

    public Page<Club> getAll(Pageable pageable) {
        return clubRepository.findAll(pageable);
    }

    public Club findById(Long id) {
        return clubRepository.findById(id).orElse(null);
    }

    public Club update(Club club, Long id) {
        Club existingClub = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found."));
        existingClub.setName(club.getName());
        existingClub.setAbv(club.getAbv());
        existingClub.setProvince(club.getProvince());
        existingClub.setStatus(club.getStatus());
        existingClub.setDescription(club.getDescription());
        existingClub.setGroupType(club.getGroupType());
        existingClub.setCategory(club.getCategory());
        return clubRepository.save(existingClub);
    }

    public void updateEmblem(MultipartFile file, Long id) throws IOException {
        Club club = clubRepository.findById(id).orElse(null);
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        saveFile(file, fileName);
        assert club != null;
        club.setEmblem(uploadPath.getClubUploadDir() + fileName);
        clubRepository.save(club);
    }


    public Club ban(Long id) {
        Club club = clubRepository.findById(id).orElse(null);
        assert club != null;
        club.setBan("Bloqueado");
        return clubRepository.save(club);
    }

    public Club unban(Long id) {
        Club club = clubRepository.findById(id).orElse(null);
        assert club != null;
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

    private String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }

    private void saveFile(MultipartFile file, String fileName) throws IOException {

        File directory = new File(uploadPath.getClubUploadDir());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Path filePath = Paths.get(uploadPath.getClubUploadDir() + fileName);
        Files.write(filePath, file.getBytes());
    }

}
