package com.example.futdabandaapi.dto;


import com.example.futdabandaapi.model.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {

    private Long id;

    private ChampionshipDto championship;

    private Field field;

    private ClubDto firstClub;

    private ClubDto secondClub;

    private Integer matchDay;

    private LocalDateTime dateTime;

    private String status;

}
