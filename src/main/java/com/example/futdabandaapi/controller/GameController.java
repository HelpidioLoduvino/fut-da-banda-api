package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.dto.GameDto;
import com.example.futdabandaapi.dto.GameStatDto;
import com.example.futdabandaapi.dto.PlayerStatDto;
import com.example.futdabandaapi.model.Game;
import com.example.futdabandaapi.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<Game> add(@RequestBody Game game, @RequestParam("championshipId") Long championshipId, @RequestParam("fieldId")  Long fieldId, @RequestParam("firstClubId") Long firstClubId, @RequestParam("secondClubId") Long secondClubId) {
        return ResponseEntity.ok(gameService.addGame(game, championshipId, fieldId, firstClubId, secondClubId));
    }

    @GetMapping
    public ResponseEntity<Page<GameDto>> getAllByMatchDay(@RequestParam("matchDay") Integer matchDay, @RequestParam("champId") Long champId, Pageable pageable) {
        return ResponseEntity.ok(gameService.getAllGamesByMatchDayAndChampionship(matchDay, champId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGame(id));
    }

    @PutMapping
    public ResponseEntity<Game> update(@RequestBody Game game, @RequestParam("id") Long id) {
        return ResponseEntity.ok(gameService.updateGame(game, id));
    }

    @PutMapping("/start")
    public ResponseEntity<Game> startGame(@RequestParam("id") Long id) {
        return ResponseEntity.ok(gameService.startGame(id));
    }

    @PutMapping("/stop")
    public ResponseEntity<Game> stopGame(@RequestParam("id") Long id) {
        return ResponseEntity.ok(gameService.stopGame(id));
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<GameStatDto>> getStats(@RequestParam("id") Long id) {
        return ResponseEntity.ok(gameService.getGameStat(id));
    }

    @GetMapping("/club-stat")
    public ResponseEntity<GameStatDto> getClubStat(@RequestParam("game") Long gameId, @RequestParam("club") Long clubId) {
        return ResponseEntity.ok(gameService.getGameStatByGameIdAndClubId(gameId, clubId));
    }

    @GetMapping("/player-stat")
    public ResponseEntity<List<PlayerStatDto>> getPlayerStat(@RequestParam("game") Long gameId) {
        return ResponseEntity.ok(gameService.getPlayerStatByGameId(gameId));
    }

    @PutMapping("/club-add-stat")
    public ResponseEntity<Void> addGameStats(@RequestParam("gameId") Long gameId, @RequestParam("clubId") Long clubId, @RequestParam("statistic") String statistic) {
        gameService.addGameStatistic(gameId, clubId, statistic);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/club-remove-stat")
    public ResponseEntity<Void> removeGameStats(@RequestParam("gameId") Long gameId, @RequestParam("clubId") Long clubId, @RequestParam("statistic") String statistic) {
        gameService.removeGameStatistic(gameId, clubId, statistic);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/player-add-stat")
    public ResponseEntity<Void> addPlayerStats(@RequestParam("gameId") Long gameId, @RequestParam("playerId") Long playerId, @RequestParam("statistic") String statistic) {
        gameService.addPlayerStatistic(gameId, playerId, statistic);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/player-remove-stat")
    public ResponseEntity<Void> removePlayerStats(@RequestParam("gameId") Long gameId, @RequestParam("playerId") Long playerId, @RequestParam("statistic") String statistic) {
        gameService.removePlayerStatistic(gameId, playerId, statistic);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/four")
    public ResponseEntity<List<GameDto>> findFourByChampionshipId(@RequestParam("id") Long id){
        return ResponseEntity.ok(gameService.findFourByChampionshipId(id));
    }



}
