package com.example.futdabandaapi.mapper;

import com.example.futdabandaapi.dto.PlayerStatDto;
import com.example.futdabandaapi.model.PlayerStat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PlayerStatMapper {
    PlayerStatMapper INSTANCE = Mappers.getMapper(PlayerStatMapper.class);
    PlayerStatDto toPlayerStatDto(PlayerStat playerStat);
    List<PlayerStatDto> toPlayerStatDto(List<PlayerStat> playerStatList);
}
