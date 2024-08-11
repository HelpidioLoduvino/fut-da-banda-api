package com.example.futdabandaapi.service;

import com.example.futdabandaapi.model.Championship;
import com.example.futdabandaapi.repository.ChampionshipRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ChampionshipService {

    private final ChampionshipRepository championshipRepository;

    public Championship createChampionship(Championship championship) {
        championship.setBan("Ativo");
        return championshipRepository.save(championship);
    }

    public List<Championship> findAll(){
        return championshipRepository.findAll();
    }

    public Page<Championship> getAllChampionships(Pageable pageable) {
        return championshipRepository.findAll(pageable);
    }

    public Championship findById(Long id) {
        return championshipRepository.findById(id).orElse(null);
    }

    public Championship update(Championship championship, Long id) {
        Championship existingChampionship = championshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field not found."));
        existingChampionship.setName(championship.getName());
        existingChampionship.setDescription(championship.getDescription());
        existingChampionship.setCategory(championship.getCategory());
        existingChampionship.setGroupType(championship.getGroupType());
        existingChampionship.setPrice(championship.getPrice());
        existingChampionship.setMatchDay(championship.getMatchDay());
        existingChampionship.setProvince(championship.getProvince());
        return championshipRepository.save(existingChampionship);

    }

    public Championship ban(Long id) {
        Championship championship = championshipRepository.findById(id).orElse(null);
        assert championship != null;
        championship.setBan("Bloqueado");
        return championshipRepository.save(championship);
    }

    public Championship unban(Long id) {
        Championship championship = championshipRepository.findById(id).orElse(null);
        assert championship != null;
        championship.setBan("Ativo");
        return championshipRepository.save(championship);
    }
}
