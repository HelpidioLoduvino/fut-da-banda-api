package com.example.futdabandaapi.dto;

import java.util.Date;

public record PlayerDto(
        Long id,
        String fullName,
        String email,
        String userRole,
        Date createdAt,
        String photo,
        String position,
        String gender,
        String biography
) {
}
