package com.example.futdabandaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChampionshipDto {
    private Long id;
    private String name;
    private String description;
    private String manualRule;
    private String category;
    private String province;
    private String type;
    private String gender;
    private String pricePer;
    private Double price;
    private String rule;
    private Integer matchDay;
    private List<ClubDto> clubs;
    private String ban;
    private LocalDate expiryDate;
    private LocalDate startDate;
}
