package com.example.futdabandaapi.dto;


public record PlayerDto(
        Long id,
        String fullName,
        String email,
        String userRole,
        String photo,
        String position,
        String gender,
        String biography
) {
}
