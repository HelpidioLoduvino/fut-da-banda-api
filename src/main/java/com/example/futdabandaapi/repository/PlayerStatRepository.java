package com.example.futdabandaapi.repository;

import com.example.futdabandaapi.model.PlayerStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerStatRepository extends JpaRepository<PlayerStat, Long> {
    List<PlayerStat> findAllByGameId(Long gameId);
    PlayerStat findByGameIdAndPlayerId(Long gameId, Long playerId);
}
