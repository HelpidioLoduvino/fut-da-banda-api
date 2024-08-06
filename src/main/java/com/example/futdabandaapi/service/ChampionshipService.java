package com.example.futdabandaapi.service;

import com.example.futdabandaapi.model.Championship;
import com.example.futdabandaapi.repository.ChampionshipRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChampionshipService {

    private final ChampionshipRepository championshipRepository;

    public Championship createChampionship(Championship championship) {
        return championshipRepository.save(championship);
    }

    public List<Championship> getAllChampionships() {
        return championshipRepository.findAll();
    }
}
