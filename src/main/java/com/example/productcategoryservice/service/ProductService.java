package com.example.productcategoryservice.service;

import com.example.productcategoryservice.entity.Product;
import com.example.productcategoryservice.entity.User;
import com.example.productcategoryservice.exception.ApiError;
import com.example.productcategoryservice.repository.ProductRepository;
import com.example.productcategoryservice.repository.UserRepository;
import com.example.productcategoryservice.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(int id) throws ApiError {
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isEmpty()) {
            throw new ApiError(HttpStatus.NOT_FOUND, 404, LocalDateTime.now(), "The requested resource does not exist");
        }
        return productById;
    }

    public Product findById(int id) throws ApiError {
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isEmpty()) {
            throw new ApiError(HttpStatus.NOT_FOUND, 404, LocalDateTime.now(), "The requested resource does not exist");
        }
        return productById.get();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(int id,int userId) throws ApiError {
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isPresent()) {
            productRepository.deleteByIdAndUserId(id,userId);
        } else {
            throw new ApiError(HttpStatus.NOT_FOUND, 404, LocalDateTime.now(), "The requested resource does not exist");
        }
    }

    public List<Product> getAllProductsByCatId(int id) {
        return productRepository.findAllByCategoryId(id);
    }


}
