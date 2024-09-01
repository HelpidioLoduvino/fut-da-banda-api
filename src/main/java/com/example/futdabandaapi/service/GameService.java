package com.example.futdabandaapi.service;

import com.example.futdabandaapi.dto.GameDto;
import com.example.futdabandaapi.dto.GameStatDto;
import com.example.futdabandaapi.dto.PlayerStatDto;
import com.example.futdabandaapi.mapper.GameMapper;
import com.example.futdabandaapi.mapper.GameStatMapper;
import com.example.futdabandaapi.mapper.PlayerStatMapper;
import com.example.futdabandaapi.model.*;
import com.example.futdabandaapi.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final ClubRepository clubRepository;
    private final ChampionshipRepository championshipRepository;
    private final ChampionshipStatRepository championshipStatRepository;
    private final FieldRepository fieldRepository;
    private final PlayerRepository playerRepository;
    private final GameStatRepository gameStatRepository;
    private final PlayerStatRepository playerStatRepository;

    public Game addGame(Game game, Long championshipId, Long fieldId, Long firstClubId, Long secondClubId) {
        try{
            Championship championship = championshipRepository.findById(championshipId).orElse(null);
            if(championship == null){
                throw new Exception("Championship not found");
            }
            Club clubA = clubRepository.findById(firstClubId).orElse(null);
            if(clubA == null){
                throw new Exception("Club not found");
            }

            Club clubB = clubRepository.findById(secondClubId).orElse(null);
            if(clubB == null){
                throw new Exception("Club not found");
            }

            Field field = fieldRepository.findById(fieldId).orElse(null);
            if(field == null){
                throw new Exception("Field not found");
            }
            game.setStatus("Marcado");
            game.setField(field);
            game.setChampionship(championship);
            game.setFirstClub(clubA);
            game.setSecondClub(clubB);
            return gameRepository.save(game);
        }catch (Exception e){
            throw new RuntimeException("Error while adding game");
        }
    }

    public Page<GameDto> getAllGamesByMatchDayAndChampionship(Integer matchDay, Long champId, Pageable pageable) {
        return gameRepository.findAllByMatchDayAndChampionshipId(matchDay, champId, pageable).map(GameMapper.INSTANCE::toGameDto);
    }

    public GameDto getGame(Long id) {
        return GameMapper.INSTANCE.toGameDto(gameRepository.findById(id).orElse(null));
    }

    public Game updateGame(Game game, Long id) {
        Game scheduledGame = gameRepository.findById(id).orElseThrow(()-> new RuntimeException("Game not found"));
        scheduledGame.setChampionship(game.getChampionship());
        scheduledGame.setMatchDay(game.getMatchDay());
        scheduledGame.setDateTime(game.getDateTime());
        scheduledGame.setFirstClub(game
                .getFirstClub());
        scheduledGame.setField(game.getField());
        scheduledGame.setSecondClub(game.getSecondClub());
        return gameRepository.save(scheduledGame);
    }

    public Game startGame(Long gameId){
        try{
            Game game = gameRepository.findById(gameId).orElse(null);
            if(game == null){
                throw new RuntimeException("Game not found");
            }
            game.setStatus("Em Andamento");
            gameRepository.save(game);
            GameStat gameStatFirstClub = new GameStat();
            GameStat gameStatSecondClub = new GameStat();
            gameStatFirstClub.setGame(game);
            gameStatFirstClub.setClub(game.getFirstClub());
            gameStatSecondClub.setGame(game);
            gameStatSecondClub.setClub(game.getSecondClub());
            gameStatRepository.save(gameStatFirstClub);
            gameStatRepository.save(gameStatSecondClub);
            return game;
        }catch (Exception e){
            throw new RuntimeException("Error while starting game");
        }
    }

    public Game stopGame(Long gameId){
        try{
            Game game = gameRepository.findById(gameId).orElse(null);
            if(game == null){
                throw new RuntimeException("Game not found");
            }
            GameStat gameStatFirstClub = gameStatRepository.findByGameIdAndClubId(game.getId(), game.getFirstClub().getId());
            if(gameStatFirstClub == null){
                throw new RuntimeException("First Club Stats not found");
            }
            GameStat gameStatSecondClub = gameStatRepository.findByGameIdAndClubId(game.getId(), game.getSecondClub().getId());
            if(gameStatSecondClub == null){
                throw new RuntimeException("Second Club Stats not found");
            }

            ChampionshipStat firstClubStat = championshipStatRepository.findByChampionshipIdAndClubId(game.getChampionship().getId(), game.getFirstClub().getId());
            if (firstClubStat == null){
                throw new RuntimeException("First Club championship Stats not found");
            }

            ChampionshipStat secondClubStat = championshipStatRepository.findByChampionshipIdAndClubId(game.getChampionship().getId(), game.getSecondClub().getId());
            if (secondClubStat == null){
                throw new RuntimeException("Second Club championship Stats not found");
            }

            if(gameStatFirstClub.getGoals() > gameStatSecondClub.getGoals()){
                firstClubStat.setPoints(firstClubStat.getPoints() + 3);
                firstClubStat.setWins(firstClubStat.getWins() + 1);
                secondClubStat.setLosses(secondClubStat.getLosses() + 1);
            } else if(gameStatFirstClub.getGoals() < gameStatSecondClub.getGoals()){
                secondClubStat.setPoints(secondClubStat.getPoints() + 3);
                secondClubStat.setWins(secondClubStat.getWins() + 1);
                firstClubStat.setLosses(firstClubStat.getLosses() + 1);
            } else{
                firstClubStat.setPoints(firstClubStat.getPoints() + 1);
                secondClubStat.setPoints(secondClubStat.getPoints() + 1);
                firstClubStat.setDraws(firstClubStat.getDraws() + 1);
                secondClubStat.setDraws(secondClubStat.getDraws() + 1);
            }
            firstClubStat.setMatches(firstClubStat.getMatches() + 1);
            secondClubStat.setMatches(secondClubStat.getMatches() + 1);
            firstClubStat.setGoalsScored(firstClubStat.getGoalsScored() + gameStatFirstClub.getGoals());
            secondClubStat.setGoalsScored(secondClubStat.getGoalsScored() + gameStatSecondClub.getGoals());
            firstClubStat.setGoalsConceded(firstClubStat.getGoalsConceded() + gameStatSecondClub.getGoals());
            secondClubStat.setGoalsConceded(secondClubStat.getGoalsConceded() + gameStatFirstClub.getGoals());
            championshipStatRepository.save(firstClubStat);
            championshipStatRepository.save(secondClubStat);
            game.setStatus("Terminado");
            return gameRepository.save(game);
        }catch (Exception e){
            throw new RuntimeException("Error while stopping game");
        }
    }

    public List<GameStatDto> getGameStat(Long gameId){
        List<GameStat> gameStats = gameStatRepository.findAllByGameId(gameId);
        return GameStatMapper.INSTANCE.toGameStatDto(gameStats);
    }

    public GameStatDto getGameStatByGameIdAndClubId(Long gameId, Long clubId){
        return GameStatMapper.INSTANCE.toGameStatDto(gameStatRepository.findByGameIdAndClubId(gameId, clubId));
    }

    public List<PlayerStatDto> getPlayerStatByGameId(Long gameId){
        List<PlayerStat> playerStats = playerStatRepository.findAllByGameId(gameId);
        return PlayerStatMapper.INSTANCE.toPlayerStatDto(playerStats);
    }

    public void addGameStatistic(Long gameId, Long clubId, String statistic) {
        GameStat gameStat = gameStatRepository.findByGameIdAndClubId(gameId, clubId);

        if (gameStat == null) {
            throw new IllegalArgumentException("GameStat not found for gameId: " + gameId + " and clubId: " + clubId);
        }

        addStatistics(gameStat, statistic);
        gameStatRepository.save(gameStat);
    }

    public void removeGameStatistic(Long gameId, Long clubId, String statistic){

        GameStat gameStat = gameStatRepository.findByGameIdAndClubId(gameId, clubId);

        if (gameStat == null) {
            throw new IllegalArgumentException("GameStat not found for gameId: " + gameId + " and clubId: " + clubId);
        }
        removeStatistics(gameStat, statistic);
        gameStatRepository.save(gameStat);
    }

    public void addPlayerStatistic(Long gameId, Long playerId, String statistic) {
        PlayerStat playerStat = playerStatRepository.findByGameIdAndPlayerId(gameId, playerId);
        Player player = playerRepository.findById(playerId).orElse(null);
        if (player == null) {
            throw new IllegalArgumentException("Player not found for gameId: " + gameId );
        }
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game == null) {
            throw new IllegalArgumentException("Game not found for gameId: " + gameId);
        }

        if(playerStat == null){
            playerStat = new PlayerStat();
            playerStat.setId(null);
            playerStat.setGame(game);
            playerStat.setPlayer(player);
            playerStat.setGoals(0);
            playerStat.setFouls(0);
            playerStat.setAssists(0);
            playerStat.setYellowCards(0);
            playerStat.setRedCards(0);
            playerStat.setBlueCards(0);
        }

        switch(statistic.toLowerCase()){
            case "goal":
                playerStat.addGoal();
                break;
            case "foul":
                playerStat.addFouls();
                break;
            case "assist":
                playerStat.addAssists();
                break;
            case "yellow":
                playerStat.addYellowCard();
                break;
            case "red":
                playerStat.addRedCard();
                break;
            case "blue":
                playerStat.addBlueCard();
                break;
        }
        playerStatRepository.save(playerStat);
    }

    public void removePlayerStatistic(Long gameId, Long playerId, String statistic) {
        PlayerStat playerStat = playerStatRepository.findByGameIdAndPlayerId(gameId, playerId);
        if(playerStat == null){
            throw new IllegalArgumentException("Player not found for gameId: " + gameId + " and clubId: " + playerId);
        }
        switch(statistic.toLowerCase()){
            case "goal":
                playerStat.removeGoal();
                break;
            case "foul":
                playerStat.removeFouls();
                break;
            case "assist":
                playerStat.removeAssist();
                break;
            case "yellow":
                playerStat.removeYellowCard();
                break;
            case "red":
                playerStat.removeRedCard();
                break;
            case "blue":
                playerStat.removeBlueCard();
                break;
        }
        playerStatRepository.save(playerStat);
    }

    private void addStatistics(GameStat gameStat, String statistic) {
        switch (statistic.toLowerCase()) {
            case "goal":
                gameStat.addGoal();
                break;
            case "foul":
                gameStat.addFouls();
                break;
            case "yellow":
                gameStat.addYellowCard();
                break;
            case "red":
                gameStat.addRedCard();
                break;
            case "blue":
                gameStat.addBlueCard();
                break;
            case "corner":
                gameStat.addCorner();
                break;
            default:
                throw new IllegalArgumentException("Unknown statistic: " + statistic);
        }
    }

    private void removeStatistics(GameStat gameStat, String statistic) {
        switch (statistic.toLowerCase()) {
            case "goal":
                gameStat.removeGoal();
                break;
            case "foul":
                gameStat.removeFouls();
                break;
            case "yellow":
                gameStat.removeYellowCard();
                break;
            case "red":
                gameStat.removeRedCard();
                break;
            case "blue":
                gameStat.removeBlueCard();
                break;
            case "corner":
                gameStat.removeCorner();
                break;
            default:
                throw new IllegalArgumentException("Unknown statistic: " + statistic);
        }
    }


    public List<GameDto> findFourByChampionshipId(Long championshipId) {
        Pageable topFour = PageRequest.of(0, 4);
        List<Game> gameStats = gameRepository.findAllByChampionshipIdOrderByIdDesc(championshipId, topFour);
        return gameStats.stream()
                .map(GameMapper.INSTANCE::toGameDto)
                .collect(Collectors.toList());
    }


}
