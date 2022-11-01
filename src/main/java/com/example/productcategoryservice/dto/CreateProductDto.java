package com.example.productcategoryservice.dto;

import com.example.productcategoryservice.entity.Category;
import com.example.productcategoryservice.entity.Product;
import com.example.productcategoryservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CreateProductDto {
    private String title;
    private int count;
    private double price;
    private Category category;
    private UserDto userDto;


}
