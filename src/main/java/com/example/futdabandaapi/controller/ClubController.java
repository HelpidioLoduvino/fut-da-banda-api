package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.model.Club;
import com.example.futdabandaapi.service.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/clubs")
@AllArgsConstructor
public class ClubController {
    private ClubService clubService;

    @PostMapping
    public ResponseEntity<Club> register(@RequestPart("club") Club club, @RequestPart("logo")MultipartFile file) throws IOException {
        return ResponseEntity.ok(clubService.addClub(club, file));
    }

    @GetMapping
    public ResponseEntity<List<Club>> getAll() {
        return ResponseEntity.ok(clubService.getAllClubs());
    }

    @DeleteMapping
    public ResponseEntity<Club> delete(@RequestParam("id") Long id) {
        clubService.deleteClub(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/display/{id}")
    public ResponseEntity<Resource> displayImage(@PathVariable Long id) {
        return ResponseEntity.ok(clubService.displayCover(id));
    }
}
