package com.example.futdabandaapi.dtos;

import java.util.Date;

public record UserDto(Long id, String name, String surname, String email, String userRole, Date createdAt) {
}
