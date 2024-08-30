package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.dto.ChampionshipDto;
import com.example.futdabandaapi.model.Championship;
import com.example.futdabandaapi.model.ChampionshipStat;
import com.example.futdabandaapi.service.ChampionshipService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/championships")
@AllArgsConstructor
public class ChampionshipController {

    private final ChampionshipService championshipService;

    @PostMapping
    public ResponseEntity<Championship> create(@RequestBody Championship championship) {
        return ResponseEntity.ok(championshipService.createChampionship(championship));
    }

    @GetMapping
    public ResponseEntity<Page<ChampionshipDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(championshipService.getAll(pageable));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ChampionshipDto>> getAllChampionships() {
        return ResponseEntity.ok(championshipService.findAll());
    }

    @GetMapping("/two")
    public ResponseEntity<List<ChampionshipDto>> getTwoChampionships() {
        return ResponseEntity.ok(championshipService.findTwo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChampionshipDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(championshipService.findById(id));
    }

    @PutMapping
    public ResponseEntity<Championship> update(@RequestBody Championship championship, @RequestParam("id") Long id) {
        return ResponseEntity.ok(championshipService.update(championship, id));
    }

    @PutMapping("/ban")
    public ResponseEntity<Championship> ban(@RequestParam Long id) {
        return ResponseEntity.ok(championshipService.ban(id));
    }

    @PutMapping("/unban")
    public ResponseEntity<Championship> unban(@RequestParam Long id) {
        return ResponseEntity.ok(championshipService.unban(id));
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ChampionshipStat>> getStats(@RequestParam("id") Long id){
        return ResponseEntity.ok(championshipService.getStatByChampionshipId(id));
    }
}
