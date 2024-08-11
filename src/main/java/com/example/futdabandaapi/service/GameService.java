package com.example.futdabandaapi.service;

import com.example.futdabandaapi.model.Game;
import com.example.futdabandaapi.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game addGame(Game game) {
        game.setStatus("Em Falta");
        return gameRepository.save(game);
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game updateGame(Game game, Long id) {
        Game scheduledGame = gameRepository.findById(id).orElseThrow(()-> new RuntimeException("Game not found"));
        scheduledGame.setChampionship(game.getChampionship());
        scheduledGame.setMatchDay(game.getMatchDay());
        scheduledGame.setDateTime(game.getDateTime());
        scheduledGame.setFirst(game.getFirst());
        scheduledGame.setField(game.getField());
        scheduledGame.setSecond(game.getSecond());
        return gameRepository.save(scheduledGame);
    }
}
