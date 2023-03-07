package com.example.productcategoryservice.endpoint;


import com.example.productcategoryservice.dto.CreateProductDto;
import com.example.productcategoryservice.dto.ProductResponseDto;
import com.example.productcategoryservice.dto.UpdateProductDto;
import com.example.productcategoryservice.entity.Product;
import com.example.productcategoryservice.exception.ApiError;
import com.example.productcategoryservice.exception.BaseException;
import com.example.productcategoryservice.mapper.ProductMapper;
import com.example.productcategoryservice.security.CurrentUser;
import com.example.productcategoryservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RequestMapping("/api/products")
@RestController
public class ProductEndpoint {
    private final ProductService productService;

    private final ProductMapper productMapper;


    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(productMapper.map(productService.getAllProducts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") int id) throws BaseException {
        try {
            return ResponseEntity.ok(productMapper.map(productService.findById(id)));
        } catch (BaseException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found", exc);
        }
    }

    @GetMapping("/by_category/{id}")
    public List<ProductResponseDto> getProductsByCatId(@PathVariable("id") int id) {
        List<Product> allProductsByCatId = productService.getAllProductsByCatId(id);
        return productMapper.map(allProductsByCatId);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@AuthenticationPrincipal CurrentUser currentUser,
                                                 @RequestBody CreateProductDto createProductDto) {
        Product userProduct = productMapper.map(createProductDto);
        userProduct.setUser(currentUser.getUser());
        return ResponseEntity.ok(productService.saveProduct(userProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@AuthenticationPrincipal CurrentUser currentUser,
                                                 @RequestBody UpdateProductDto updateProductDto,
                                                 @PathVariable("id") int id) throws ApiError {
        Product userProduct = productMapper.map(updateProductDto);
        userProduct.setUser(currentUser.getUser());
        productService.saveProduct(userProduct);
        Optional<Product> productById = productService.findProductById(id);
        if (productById.isPresent() && currentUser.getUser().getId() == productById.get().getUser().getId()) {
            return ResponseEntity.ok((productService.saveProduct(productMapper.map(updateProductDto))));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById( @PathVariable("id") int id,
                                                 @AuthenticationPrincipal CurrentUser currentUser) {
        try {
            productService.deleteProductById(id, currentUser);
            return ResponseEntity.ok(productMapper.map(productService.getAllProducts()));
        } catch (BaseException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found", exc);
        }
    }
}

