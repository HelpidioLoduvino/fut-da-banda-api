package com.example.futdabandaapi.service;

import com.example.futdabandaapi.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticService {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final ChampionshipRepository championshipRepository;
    private final FieldRepository fieldRepository;

    public long countUsers(){
        return userRepository.count();
    }

    public long countPlayers(){
        return playerRepository.count();
    }

    public long countClubs(){
        return clubRepository.count();
    }

    public long countChampionships(){
        return championshipRepository.count();
    }

    public long countFields(){
        return fieldRepository.count();
    }
}
