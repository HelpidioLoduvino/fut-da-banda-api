package com.example.futdabandaapi.repository;

import com.example.futdabandaapi.model.Championship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
}
