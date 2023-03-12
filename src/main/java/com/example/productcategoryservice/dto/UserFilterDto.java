package com.example.productcategoryservice.dto;

import com.example.productcategoryservice.entity.Role;
import lombok.Data;

@Data
public class UserFilterDto {
    private String name;
    private String surname;
    private String email;
    private Role role;

}
