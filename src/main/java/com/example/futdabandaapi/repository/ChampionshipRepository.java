package com.example.futdabandaapi.repository;

import com.example.futdabandaapi.model.Championship;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    List<Championship> findAllByOrderByIdDesc(Pageable topTwo);
}
