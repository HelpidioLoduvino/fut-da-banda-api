package com.example.futdabandaapi.controller;

import com.example.futdabandaapi.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@AllArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping("/users")
    public ResponseEntity<Object> countUsers(){
     return ResponseEntity.ok(statisticService.countUsers());
    }

    @GetMapping("/players")
    public ResponseEntity<Object> countPlayers(){
        return ResponseEntity.ok(statisticService.countPlayers());
    }

    @GetMapping("/clubs")
    public ResponseEntity<Object> countClubs(){
        return ResponseEntity.ok(statisticService.countClubs());
    }

    @GetMapping("/championships")
    public ResponseEntity<Object> countChampionships(){
        return ResponseEntity.ok(statisticService.countChampionships());
    }

    @GetMapping("/fields")
    public ResponseEntity<Object> countFields(){
        return ResponseEntity.ok(statisticService.countFields());
    }

}
