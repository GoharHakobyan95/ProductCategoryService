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
public class CreateUserDto {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private Role role;
}
