package com.example.futdabandaapi.repository;

import com.example.futdabandaapi.model.GameStat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameStatRepository extends JpaRepository<GameStat, Long> {
    List<GameStat> findAllByGameId(Long gameId);
    GameStat findByGameIdAndClubId(Long gameId, Long clubId);
    List<GameStat> findAllByGameChampionshipIdOrderByIdDesc(Long gameChampionshipId, Pageable topFour);
}
