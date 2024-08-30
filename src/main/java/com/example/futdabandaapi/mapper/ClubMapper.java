package com.example.futdabandaapi.mapper;

import com.example.futdabandaapi.dto.ClubDto;
import com.example.futdabandaapi.model.Club;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClubMapper {
    ClubMapper INSTANCE = Mappers.getMapper(ClubMapper.class);
    ClubDto toClubDto(Club club);
}
