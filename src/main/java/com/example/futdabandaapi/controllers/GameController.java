package com.example.futdabandaapi.controllers;

import com.example.futdabandaapi.entities.Game;
import com.example.futdabandaapi.services.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@AllArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/add")
    public ResponseEntity<Game> add(@RequestBody Game game) {
        return ResponseEntity.ok(gameService.addGame(game));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Game>> getAll() {
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @PutMapping("/update")
    public ResponseEntity<Game> update(@RequestBody Game game, @RequestParam Long id) {
        return ResponseEntity.ok(gameService.updateGame(game, id));
    }

}
