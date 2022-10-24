package com.example.productcategoryservice.mapper;


import com.example.productcategoryservice.dto.CategoryResponseDto;
import com.example.productcategoryservice.dto.CreateCategoryDto;
import com.example.productcategoryservice.dto.UpdateCategoryDto;
import com.example.productcategoryservice.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category map(CreateCategoryDto createCategoryDto);

    CategoryResponseDto map(Category category);

    List<CategoryResponseDto> map(List<Category> categories);

    Category map(UpdateCategoryDto updateCategoryDto);
}
