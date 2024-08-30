package com.example.futdabandaapi.repository;

import com.example.futdabandaapi.model.Club;
import com.example.futdabandaapi.model.JoinChampionship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinChampionshipRepository extends JpaRepository<JoinChampionship, Long> {
    List<JoinChampionship> findAllByClubAndStatus(Club club, String status);
    List<JoinChampionship> findAllByStatus(String status);
}
