package com.example.productcategoryservice.mapper;

import com.example.productcategoryservice.dto.CreateProductDto;
import com.example.productcategoryservice.dto.ProductResponseDto;
import com.example.productcategoryservice.dto.UpdateProductDto;
import com.example.productcategoryservice.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ProductMapper {
    Product map(CreateProductDto createProductDto);

    ProductResponseDto map(Product product);

    List<ProductResponseDto> map(List<Product> products);
    Product map(UpdateProductDto updateProductDto);
}
