package com.example.futdabandaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDto {
    private Long id;
    private String name;
    private String abv;
    private String province;
    private String status;
    private String description;
    private String competition;
    private String category;
    private String admissionTest;
    private String gender;
    private List<PlayerDto> players = new ArrayList<>();
    private String ban;
}
