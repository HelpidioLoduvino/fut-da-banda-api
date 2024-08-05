package com.example.futdabandaapi.dtos;

import java.time.LocalDate;
import java.util.Date;

public record UserDto(Long id, String fullName, String email, String userRole, LocalDate createdAt) {
}
