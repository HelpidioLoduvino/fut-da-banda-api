package com.example.futdabandaapi.mapper;

import com.example.futdabandaapi.dto.ChampionshipDto;
import com.example.futdabandaapi.model.Championship;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChampionshipMapper {
    ChampionshipMapper INSTANCE = Mappers.getMapper(ChampionshipMapper.class);
    ChampionshipDto toChampionshipDto(Championship championship);
}
