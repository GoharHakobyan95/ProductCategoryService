package com.example.productcategoryservice.endpoint;

import com.example.productcategoryservice.dto.*;
import com.example.productcategoryservice.entity.Product;
import com.example.productcategoryservice.entity.User;
import com.example.productcategoryservice.exception.ApiError;
import com.example.productcategoryservice.mapper.ProductMapper;
import com.example.productcategoryservice.security.CurrentUser;
import com.example.productcategoryservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") int id) throws ApiError {
        try {
            return ResponseEntity.ok(productMapper.map(productService.findById(id)));
        } catch (ApiError exc) {
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
        User user = currentUser.getUser();
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setPhone(user.getPhone());
        userDto.setRole(user.getRole());
        createProductDto.setUserDto(userDto);
        return ResponseEntity.ok(productService.saveProduct(productMapper.map(createProductDto)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@AuthenticationPrincipal CurrentUser currentUser,
                                                 @RequestBody UpdateProductDto updateProductDto,
                                                 @PathVariable("id") int id) throws ApiError {
        Optional<Product> productById = productService.findProductById(id);
        if (currentUser.getUser().getId() == productById.get().getUser().getId()) {
            return ResponseEntity.ok((productService.saveProduct(productMapper.map(updateProductDto))));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@AuthenticationPrincipal CurrentUser currentUser,
                                                @PathVariable("id") int id) {
        try {
            productService.deleteProductById(id, currentUser.getUser().getId());
            return ResponseEntity.ok(productMapper.map(productService.getAllProducts()));
        } catch (ApiError exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found", exc);
        }
    }
}

