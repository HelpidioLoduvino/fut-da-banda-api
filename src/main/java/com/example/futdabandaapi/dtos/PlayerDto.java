package com.example.futdabandaapi.dtos;

import com.example.futdabandaapi.entities.Player;
import com.example.futdabandaapi.entities.User;

public record PlayerDto(User user, Player player) {
}
