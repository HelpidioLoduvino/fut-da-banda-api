package com.example.futdabandaapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChampionshipStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Championship championship;

    @ManyToOne
    private Club club;

    private Integer points;

    private Integer matches;

    private Integer wins;

    private Integer losses;

    private Integer draws;

    private Integer goalsScored;

    private Integer goalsConceded;

    private Integer goalsDifference;

    @PrePersist
    protected void onCreate() {
        points = 0;
        matches = 0;
        wins = 0;
        losses = 0;
        draws = 0;
        goalsScored = 0;
        goalsConceded = 0;
        goalsDifference = 0;
    }

}
