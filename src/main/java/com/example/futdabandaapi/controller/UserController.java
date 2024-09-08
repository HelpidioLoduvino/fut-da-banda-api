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

import java.io.IOException;

@RestController
@RequestMapping("/users")
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
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllExceptAdmin(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<PlayerDto> findByPlayerId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findByPlayerId(id));
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> findByUserRole(@RequestParam("role") String role) {
        return ResponseEntity.ok(userService.findByUserRole(role));
    }

    @GetMapping("/authenticated")
    public ResponseEntity<UserDto> getAuthenticated() {
        return ResponseEntity.ok(userService.getAuthenticated());
    }

    @GetMapping("/players")
    public ResponseEntity<Page<PlayerDto>> getAllAvailablePlayers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllAvailablePlayers(pageable));
    }

    @GetMapping("/player")
    public ResponseEntity<PlayerDto> getPlayer() {
        return ResponseEntity.ok(userService.findAuthenticatedPlayer());
    }

    @GetMapping("/role")
    public ResponseEntity<String> findUserRole(){
        return ResponseEntity.ok(userService.findUserRole());
    }

    @PutMapping("/ban")
    public ResponseEntity<User> ban(@RequestParam Long id) {
        return ResponseEntity.ok(userService.ban(id));
    }

    @PutMapping("/unban")
    public ResponseEntity<User> unban(@RequestParam Long id) {
        return ResponseEntity.ok(userService.unban(id));
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestParam Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(user, id));
    }

    @PutMapping("/player")
    public ResponseEntity<Player> updatePlayer(@RequestParam Long id, @RequestBody Player player) {
        return ResponseEntity.ok(userService.updatePlayer(player, id));
    }

    @PutMapping("/photo")
    public ResponseEntity<Object> updatePhoto(@RequestParam Long id, @RequestParam("photo") MultipartFile photo) throws IOException {
        userService.updatePhoto(photo, id);
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

    @GetMapping("/available")
    public ResponseEntity<Boolean> isPlayerAvailable(){
        return ResponseEntity.ok(userService.isAvailable());
    }

}
