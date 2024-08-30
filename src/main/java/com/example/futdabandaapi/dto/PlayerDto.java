package com.example.futdabandaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto{
    private Long id;
    private String fullName;
    private String email;
    private String userRole;
    private String photo;
    private String position;
    private String gender;
    private String biography;
    private String available;
}
