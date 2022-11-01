package com.example.productcategoryservice.dto;

import com.example.productcategoryservice.entity.Category;
import com.example.productcategoryservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ProductResponseDto {
    private int id;
    private String title;
    private double price;
    private Category category;
    private User user;
}
