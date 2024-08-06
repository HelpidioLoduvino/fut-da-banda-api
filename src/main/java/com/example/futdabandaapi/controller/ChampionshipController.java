package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.model.Championship;
import com.example.futdabandaapi.service.ChampionshipService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/championships")
@AllArgsConstructor
public class ChampionshipController {

    private final ChampionshipService championshipService;

    @PostMapping
    public ResponseEntity<Championship> create(@RequestBody Championship championship) {
        return ResponseEntity.ok(championshipService.createChampionship(championship));
    }

    @GetMapping
    public ResponseEntity<List<Championship>> getAll() {
        return ResponseEntity.ok(championshipService.getAllChampionships());
    }
}
