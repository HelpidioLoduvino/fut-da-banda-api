package com.example.futdabandaapi.repository;

import com.example.futdabandaapi.model.Club;
import com.example.futdabandaapi.model.Player;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    boolean existsByPlayersContaining(Player player);
    Club findByPlayersContaining(Player player);
    List<Club> findAllByOrderByIdDesc(Pageable pageable);
}
