package com.example.productcategoryservice.endpoint;

import com.example.productcategoryservice.dto.CreateProductDto;
import com.example.productcategoryservice.dto.ProductResponseDto;
import com.example.productcategoryservice.dto.UpdateProductDto;
import com.example.productcategoryservice.entity.Product;
import com.example.productcategoryservice.mapper.ProductMapper;
import com.example.productcategoryservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ProductEndpoint {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/products")
    public List<ProductResponseDto> getAllCategories() {
        return productMapper.map(productService.getAllProducts());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        Optional<Product> byId = productService.findProductById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(byId.get());
    }

    @GetMapping("/products/by_category/{id}")
    public List<ProductResponseDto> getProductsByCatId(@PathVariable("id") int id) {
        Optional<Product> productById = productService.findProductById(id);
        List<Product> allProductsByCatId = productService.getAllProductsByCatId(id);
        return productMapper.map(allProductsByCatId);
    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductDto createProductDto) {
        return ResponseEntity.ok(productService.saveProduct(productMapper.map(createProductDto)));
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProductDto updateProductDto,
                                           @PathVariable("id") int id) {
        Optional<Product> catById = productService.findProductById(id);
        if (catById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok((productService.saveProduct(productMapper.map(updateProductDto))));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") int id) {
        Optional<Product> catById = productService.findProductById(id);
        if (catById.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            productService.deleteProductById(id);
            return ResponseEntity.ok(productMapper.map(productService.getAllProducts()));
        }
    }
}
