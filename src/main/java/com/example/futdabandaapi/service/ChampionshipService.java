package com.example.futdabandaapi.service;

import com.example.futdabandaapi.dto.ChampionshipDto;
import com.example.futdabandaapi.mapper.ChampionshipMapper;
import com.example.futdabandaapi.model.*;
import com.example.futdabandaapi.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Transactional
public class ChampionshipService {

    private final ChampionshipRepository championshipRepository;
    private final ChampionshipStatRepository championshipStatRepository;

    public Championship createChampionship(Championship championship) {
        championship.setBan("Ativo");
        return championshipRepository.save(championship);
    }

    public List<ChampionshipDto> findAll() {
        return championshipRepository.findAll().stream()
                .map(ChampionshipMapper.INSTANCE::toChampionshipDto)
                .collect(Collectors.toList());
    }

    public List<ChampionshipDto> findTwo() {
        Pageable topTwo = PageRequest.of(0, 2);
        List<Championship> championships = championshipRepository.findAllByOrderByIdDesc(topTwo);
        return championships.stream()
                .map(ChampionshipMapper.INSTANCE::toChampionshipDto)
                .collect(Collectors.toList());
    }


    public Page<ChampionshipDto> getAll(Pageable pageable) {
        return championshipRepository.findAll(pageable)
                .map(ChampionshipMapper.INSTANCE::toChampionshipDto);
    }

    public ChampionshipDto findById(Long id) {
        Championship championship = championshipRepository.findById(id).orElse(null);
        return ChampionshipMapper.INSTANCE.toChampionshipDto(championship);
    }

    public Championship update(Championship championship, Long id) {
        Championship existingChampionship = championshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field not found."));
        existingChampionship.setName(championship.getName());
        existingChampionship.setDescription(championship.getDescription());
        existingChampionship.setCategory(championship.getCategory());
        existingChampionship.setType(championship.getType());
        existingChampionship.setRule(championship.getRule());
        existingChampionship.setGender(championship.getGender());
        existingChampionship.setExpiryDate(championship.getExpiryDate());
        existingChampionship.setStartDate(championship.getStartDate());
        existingChampionship.setManualRule(championship.getManualRule());
        existingChampionship.setPricePer(championship.getPricePer());
        existingChampionship.setPrice(championship.getPrice());
        existingChampionship.setMatchDay(championship.getMatchDay());
        existingChampionship.setProvince(championship.getProvince());
        return championshipRepository.save(existingChampionship);

    }

    public Championship ban(Long id) {
        Championship championship = championshipRepository.findById(id).orElse(null);
        if(championship == null){
            return null;
        }
        championship.setBan("Bloqueado");
        return championshipRepository.save(championship);
    }

    public Championship unban(Long id) {
        Championship championship = championshipRepository.findById(id).orElse(null);
        if(championship == null){
            return null;
        }
        championship.setBan("Ativo");
        return championshipRepository.save(championship);
    }

    public List<ChampionshipStat> getStatByChampionshipId(Long championshipId) {
        return championshipStatRepository.findAllByChampionshipIdOrderByPointsDesc(championshipId);
    }

}
