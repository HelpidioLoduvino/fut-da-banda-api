package com.example.futdabandaapi.controllers;

import com.example.futdabandaapi.dtos.LoginDto;
import com.example.futdabandaapi.dtos.PlayerDto;
import com.example.futdabandaapi.dtos.TokenRefreshRequestDto;
import com.example.futdabandaapi.dtos.UserDto;
import com.example.futdabandaapi.entities.Player;
import com.example.futdabandaapi.entities.User;
import com.example.futdabandaapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/register-player")
    public ResponseEntity<Object> registerPlayer(@RequestPart("player") Player player, @RequestPart("photo") MultipartFile photo) {
        return userService.registerPlayer(player, photo);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto user) {
        return userService.login(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<Object> refresh(@RequestBody TokenRefreshRequestDto request) {
        return ResponseEntity.ok(userService.refresh(request));
    }

}
