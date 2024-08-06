package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.model.Game;
import com.example.futdabandaapi.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@AllArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<Game> add(@RequestBody Game game) {
        return ResponseEntity.ok(gameService.addGame(game));
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAll() {
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @PutMapping
    public ResponseEntity<Game> update(@RequestBody Game game, @RequestParam Long id) {
        return ResponseEntity.ok(gameService.updateGame(game, id));
    }

}
