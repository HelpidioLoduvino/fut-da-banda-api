package com.example.futdabandaapi.repository;

import com.example.futdabandaapi.model.ChampionshipStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChampionshipStatRepository extends JpaRepository<ChampionshipStat, Long> {
    List<ChampionshipStat> findAllByChampionshipIdOrderByPointsDesc(Long championshipId);
    ChampionshipStat findByChampionshipIdAndClubId(Long championshipId, Long clubId);
}
