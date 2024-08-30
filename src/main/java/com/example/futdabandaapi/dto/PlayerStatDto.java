package com.example.futdabandaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStatDto{

    private Long id;

    private GameDto game;

    PlayerDto player;

    private Integer assists;

    private Integer fouls;

    private Integer goals;

    private Integer yellowCards;

    private Integer redCards;

    private Integer blueCards;
}
