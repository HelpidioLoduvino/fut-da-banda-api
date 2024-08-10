package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.dto.*;
import com.example.futdabandaapi.model.Player;
import com.example.futdabandaapi.model.User;
import com.example.futdabandaapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/player")
    public ResponseEntity<Player> registerPlayer(@RequestPart("player") Player player, @RequestPart("photo") MultipartFile photo) {
        return ResponseEntity.ok(userService.registerPlayer(player, photo));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto user) {
       return ResponseEntity.ok(userService.login(user));
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllExceptAdmin(pageable));
    }

    @GetMapping("/players")
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        return ResponseEntity.ok(userService.getAllPlayers());
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteUser(@RequestParam Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token")
    public ResponseEntity<Object> refresh(@RequestBody TokenRefreshRequestDto request) {
        return ResponseEntity.ok(userService.refresh(request));
    }

    @GetMapping("/display/{id}")
    public ResponseEntity<Resource> showPhoto(@PathVariable Long id) {
        return ResponseEntity.ok(userService.showPhoto(id));
    }

}
