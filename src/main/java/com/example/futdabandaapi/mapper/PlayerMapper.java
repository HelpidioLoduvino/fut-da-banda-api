package com.example.futdabandaapi.mapper;

import com.example.futdabandaapi.dto.PlayerDto;
import com.example.futdabandaapi.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);
    PlayerDto toPlayerDto(Player player);
}
