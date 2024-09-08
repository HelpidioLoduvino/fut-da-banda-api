package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.dto.ClubDto;
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
import java.util.List;

@RestController
@RequestMapping("/clubs")
@AllArgsConstructor
public class ClubController {
    private final ClubService clubService;

    @PostMapping
    public ResponseEntity<Club> register(@RequestPart("club") Club club, @RequestPart("logo")MultipartFile file) throws IOException {
        return ResponseEntity.ok(clubService.add(club, file));
    }

    @GetMapping
    public ResponseEntity<Page<ClubDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(clubService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubDto> getClubById(@PathVariable Long id) {
        return ResponseEntity.ok(clubService.findById(id));
    }

    @GetMapping("/exists")
    public ResponseEntity<Club> findClubIfExists(){
        return ResponseEntity.ok(clubService.findClubIfExists());
    }

    @GetMapping("/player")
    public ResponseEntity<Boolean> playerHasClub(){
        return ResponseEntity.ok(clubService.playerHasClub());
    }

    @GetMapping("/club")
    public ResponseEntity<ClubDto> getPlayerClub(){
        return ResponseEntity.ok(clubService.getPlayerClub());
    }

    @GetMapping("/captain")
    public ResponseEntity<Boolean> isPlayerClubCaptain(@RequestParam("id") Long id){
        return ResponseEntity.ok(clubService.isPlayerClubCaptain(id));
    }

    @PutMapping
    public ResponseEntity<Club> update(@RequestBody Club club, @RequestParam("id") Long id) {
        return ResponseEntity.ok(clubService.update(club, id));
    }

    @PutMapping("/emblem")
    public ResponseEntity<Object> updateEmblem(@RequestPart("emblem") MultipartFile file, @RequestParam("id") Long id) throws IOException {
        clubService.updateEmblem(file, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/ban")
    public ResponseEntity<Club> ban(@RequestParam Long id) {
        return ResponseEntity.ok(clubService.ban(id));
    }

    @PutMapping("/unban")
    public ResponseEntity<Club> unban(@RequestParam Long id) {
        return ResponseEntity.ok(clubService.unban(id));
    }

    @GetMapping("/display/{id}")
    public ResponseEntity<Resource> displayImage(@PathVariable Long id) {
        return ResponseEntity.ok(clubService.displayCover(id));
    }

    @GetMapping("/available")
    public ResponseEntity<Boolean> isAvailable(@RequestParam("id") Long id){
        return ResponseEntity.ok(clubService.clubIsAvailableToCompete(id));
    }

    @GetMapping("/four")
    public ResponseEntity<List<ClubDto>> getFour(){
        return ResponseEntity.ok(clubService.getFour());
    }
}
