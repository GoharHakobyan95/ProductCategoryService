package com.example.productcategoryservice.dto;

import com.example.productcategoryservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UserAuthResponseDto {
    private String token;
    private UserDto user;
}
