package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.model.Club;
import com.example.futdabandaapi.service.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/clubs")
@AllArgsConstructor
public class ClubController {
    private final ClubService clubService;

    @PostMapping
    public ResponseEntity<Club> register(@RequestPart("club") Club club, @RequestPart("logo")MultipartFile file) throws IOException {
        return ResponseEntity.ok(clubService.add(club, file));
    }

    @GetMapping
    public ResponseEntity<Page<Club>> getAll(Pageable pageable) {
        return ResponseEntity.ok(clubService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Club> getClubById(@PathVariable Long id) {
        return ResponseEntity.ok(clubService.findById(id));
    }

    @PutMapping
    public ResponseEntity<Club> update(@RequestBody Club club, @RequestParam("id") Long id) {
        return ResponseEntity.ok(clubService.update(club, id));
    }

    @PutMapping("/emblem")
    public ResponseEntity<Object> updateEmblem(@RequestPart("emblem") MultipartFile file, @RequestParam("id") Long id) throws IOException {
        clubService.uploadFile(file, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Club> delete(@RequestParam("id") Long id) {
        clubService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/display/{id}")
    public ResponseEntity<Resource> displayImage(@PathVariable Long id) {
        return ResponseEntity.ok(clubService.displayCover(id));
    }
}
