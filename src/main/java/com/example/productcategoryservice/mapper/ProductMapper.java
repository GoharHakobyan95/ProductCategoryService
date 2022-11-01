package com.example.productcategoryservice.mapper;

import com.example.productcategoryservice.dto.CreateProductDto;
import com.example.productcategoryservice.dto.ProductResponseDto;
import com.example.productcategoryservice.dto.UpdateProductDto;
import com.example.productcategoryservice.entity.Product;
import com.example.productcategoryservice.security.CurrentUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ProductMapper {

   Product map(CreateProductDto createProductDto);

    List<ProductResponseDto> map(List<Product> productList);

    ProductResponseDto map(Product product);

    Product map(UpdateProductDto updateProductDto);

}
