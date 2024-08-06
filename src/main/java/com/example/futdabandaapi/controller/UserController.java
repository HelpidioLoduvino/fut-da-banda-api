package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.dto.*;
import com.example.futdabandaapi.model.Player;
import com.example.futdabandaapi.model.User;
import com.example.futdabandaapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
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
    public ResponseEntity<Player> registerPlayer(@RequestPart("player") Player player, @RequestPart("photo") MultipartFile photo) {
        return ResponseEntity.ok(userService.registerPlayer(player, photo));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto user) {
       return ResponseEntity.ok(userService.login(user));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/players")
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        return ResponseEntity.ok(userService.getAllPlayers());
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }


    @DeleteMapping
    public ResponseEntity<Object> deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
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
