package com.example.futdabandaapi.dtos;

import java.util.Date;

public record UserDto(Long id, String fullName, String email, String userRole, Date createdAt) {
}
