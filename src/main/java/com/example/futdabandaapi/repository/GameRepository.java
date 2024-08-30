package com.example.futdabandaapi.repository;

import com.example.futdabandaapi.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Page<Game> findAllByChampionshipId(Long championshipId, Pageable pageable);
    Page<Game> findAllByMatchDayAndChampionshipId(Integer matchDay, Long champId, Pageable pageable);
    List<Game> findAllByChampionshipIdOrderByIdDesc(Long id, Pageable topFour);
}
