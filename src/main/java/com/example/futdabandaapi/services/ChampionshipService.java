package com.example.futdabandaapi.services;

import com.example.futdabandaapi.entities.Championship;
import com.example.futdabandaapi.repositories.ChampionshipRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChampionshipService {

    private final ChampionshipRepository championshipRepository;

    public ResponseEntity<Championship> createChampionship(Championship championship) {
        return ResponseEntity.ok(championshipRepository.save(championship));
    }

    public ResponseEntity<List<Championship>> getAllChampionships() {
        return ResponseEntity.ok(championshipRepository.findAll());
    }
}
