package com.example.futdabandaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameStatDto {

    private Long id;

    private GameDto game;

    private ClubDto club;

    private Integer fouls;

    private Integer goals;

    private Integer corners;

    private Integer yellowCards;

    private Integer redCards;

    private Integer blueCards;

}
