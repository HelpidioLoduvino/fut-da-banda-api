package com.example.futdabandaapi.mapper;

import com.example.futdabandaapi.dto.GameStatDto;
import com.example.futdabandaapi.model.GameStat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GameStatMapper {
    GameStatMapper INSTANCE = Mappers.getMapper(GameStatMapper.class);
    GameStatDto toGameStatDto(GameStat gameStat);
    List<GameStatDto> toGameStatDto(List<GameStat> gameStatList);
}
