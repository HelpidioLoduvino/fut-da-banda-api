package com.example.futdabandaapi.controllers;

import com.example.futdabandaapi.entities.Championship;
import com.example.futdabandaapi.services.ChampionshipService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/championship")
@AllArgsConstructor
public class ChampionshipController {

    private final ChampionshipService championshipService;

    @PostMapping("/create")
    public ResponseEntity<Championship> create(@RequestBody Championship championship) {
        return ResponseEntity.ok(championshipService.createChampionship(championship).getBody());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Championship>> getAll() {
        return ResponseEntity.ok(championshipService.getAllChampionships().getBody());
    }
}
