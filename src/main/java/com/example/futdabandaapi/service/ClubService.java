package com.example.futdabandaapi.service;

import com.example.futdabandaapi.model.Club;
import com.example.futdabandaapi.model.User;
import com.example.futdabandaapi.repository.ClubRepository;
import com.example.futdabandaapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
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
    private final UserService userService;
    private final UploadPath uploadPath;

    public Club addClub(Club club, MultipartFile file) throws IOException {

        String email = userService.getCurrentUser();

        User user = userRepository.findByUserEmail(email);

        String filename = generateUniqueFileName(file.getOriginalFilename());
        saveFile(file, filename);
        club.setBadge(uploadPath.getClubUploadDir() + filename);
        club.getPlayers().add(user);
        club.setReadyToPlay(false);
        return clubRepository.save(club);
    }

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public void deleteClub(Long id){
        clubRepository.deleteById(id);
    }

    public Resource displayCover(Long id){
        try{
            Club club = clubRepository.findById(id).orElse(null);
            if(club != null){
                Path path = Paths.get(club.getBadge());
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
