package com.example.futdabandaapi.service;

import com.example.futdabandaapi.dto.*;
import com.example.futdabandaapi.model.Player;
import com.example.futdabandaapi.model.User;
import com.example.futdabandaapi.repository.PlayerRepository;
import com.example.futdabandaapi.repository.UserRepository;
import com.example.futdabandaapi.security.TokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationContext applicationContext;
    private final TokenService tokenService;
    private final UploadPath uploadPath;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public Player registerPlayer(Player player, MultipartFile photo) {
        try {
            if(this.userRepository.findByEmail(player.getEmail()) != null) throw new RuntimeException("Player already exists");

            String encodedPassword = passwordEncoder.encode(player.getPassword());

            String filename = generateUniqueFileName(photo.getOriginalFilename());

            saveFile(photo, filename);

            Player newPlayer = new Player(
                    null,
                    player.getFullName(),
                    player.getEmail(),
                    encodedPassword,
                    player.getUserRole(),
                    null,
                    uploadPath.getPlayerUploadDir() + filename,
                    player.getPosition(),
                    player.getGender(),
                    player.getBiography()
            );

            return playerRepository.save(newPlayer);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public LoginResponseDto login(@RequestBody LoginDto loginDTO){
        AuthenticationManager authenticationManager = applicationContext.getBean(AuthenticationManager.class);
        var emailPassword = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
        Authentication authentication = authenticationManager.authenticate(emailPassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        var token = tokenService.generateToken((User) authentication.getPrincipal());
        var refreshToken = tokenService.generateRefreshToken((User) authentication.getPrincipal());
        String email = user.getEmail();
        String userRole = user.getAuthorities().iterator().next().getAuthority();
        Long id = user.getId();
        return new LoginResponseDto(id, token, refreshToken, email, userRole);
    }

    public ResponseEntity<?> refresh(@RequestBody TokenRefreshRequestDto request) {
        try {
            String email = tokenService.validateToken(request.refreshToken());
            User user = new User();
            user.setEmail(email);
            String newAccessToken = tokenService.generateToken(user);
            String newRefreshToken = tokenService.generateRefreshToken(user);

            return ResponseEntity.ok(new TokenResponseDto(newAccessToken, newRefreshToken));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid Refresh Token");
        }
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public List<PlayerDto> getAllPlayers() {
        return playerRepository.findAllPlayers();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
        }

        return username;
    }

    private String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }

    private void saveFile(MultipartFile file, String fileName) throws IOException {

        File directory = new File(uploadPath.getPlayerUploadDir());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Path filePath = Paths.get(uploadPath.getPlayerUploadDir() + fileName);
        Files.write(filePath, file.getBytes());
    }

    public Resource showPhoto(Long id){
        try{
            Player player = playerRepository.findById(id).orElse(null);
            if(player != null){
                Path path = Paths.get(player.getPhoto());
                return getResourceResponseEntity(path);
            }
        }catch (MalformedURLException e) {
            throw new RuntimeException("Erro: " + e.getMessage());
        }
        return null;
    }

    static Resource getResourceResponseEntity(Path path) throws MalformedURLException {
        Resource resource = new UrlResource(path.toUri());
        if(resource.exists() || resource.isReadable()){
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource).getBody();
        } else {
            return null;
        }
    }

}
