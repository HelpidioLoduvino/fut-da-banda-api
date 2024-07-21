package com.example.futdabandaapi.services;

import com.example.futdabandaapi.entities.Club;
import com.example.futdabandaapi.entities.User;
import com.example.futdabandaapi.repositories.ClubRepository;
import com.example.futdabandaapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import static com.example.futdabandaapi.services.UserService.getResourceResponseEntity;

@Service
@AllArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private static final String uploadDir = "src/main/resources/static/club/";

    public ResponseEntity<Club> addClub(Club club, MultipartFile file) throws IOException {

        String email = userService.getCurrentUser();

        User user = userRepository.findByUserEmail(email);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String filename = generateUniqueFileName(file.getOriginalFilename());
        saveFile(file, filename);
        club.setBadge(uploadDir + filename);
        club.setUser(user);
        club.setReadyToPlay(false);
        clubRepository.save(club);
        return ResponseEntity.ok(club);
    }

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public void deleteClub(Long id){
        clubRepository.deleteById(id);
    }

    public ResponseEntity<Resource> displayCover(Long id){
        try{
            Club club = clubRepository.findById(id).orElse(null);
            if(club != null){
                Path path = Paths.get(club.getBadge());
                return getResourceResponseEntity(path);
            }
        }catch (MalformedURLException e) {
            throw new RuntimeException("Erro: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    private String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }

    private void saveFile(MultipartFile file, String fileName) throws IOException {

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Path filePath = Paths.get(uploadDir + fileName);
        Files.write(filePath, file.getBytes());
    }

}
