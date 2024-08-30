package com.example.futdabandaapi.mapper;

import com.example.futdabandaapi.dto.GameDto;
import com.example.futdabandaapi.model.Game;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameMapper {
    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);
    GameDto toGameDto(Game game);
}
