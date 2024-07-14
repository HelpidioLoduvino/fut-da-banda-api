package com.example.futdabandaapi.dtos;

public record LoginResponseDto(Long id, String token, String refreshToken, String email, String userRole) {
}
