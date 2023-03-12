package com.example.productcategoryservice.mapper;

import com.example.productcategoryservice.dto.CreateUserDto;
import com.example.productcategoryservice.dto.UserDto;
import com.example.productcategoryservice.dto.UserResponseDto;
import com.example.productcategoryservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role", target = "role")
    User map(CreateUserDto createUserDto);

    @Mapping(source = "role", target = "role")
    UserDto map(User user);



}
