package com.example.futdabandaapi.dto;

import java.util.Date;

public record UserDto(Long id, String fullName, String email, String userRole, Date createdAt) {
}
