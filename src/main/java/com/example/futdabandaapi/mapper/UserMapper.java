package com.example.futdabandaapi.mapper;

import com.example.futdabandaapi.dto.UserDto;
import com.example.futdabandaapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto toUserDto(User user);
}
