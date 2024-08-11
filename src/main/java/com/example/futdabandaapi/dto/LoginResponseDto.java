package com.example.futdabandaapi.dto;

public record LoginResponseDto(String token, String refreshToken, String userRole, String status) {
}
