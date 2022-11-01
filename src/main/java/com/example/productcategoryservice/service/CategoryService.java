package com.example.productcategoryservice.service;

import com.example.productcategoryservice.entity.Category;
import com.example.productcategoryservice.exception.ApiError;
import com.example.productcategoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCats() {
        return categoryRepository.findAll();
    }

    public Category findCatById(int id) throws ApiError {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ApiError(HttpStatus.NOT_FOUND, 404, LocalDateTime.now(), "The requested resource does not exist");
        }
        return categoryRepository.findById(id).get();
    }

    public Category saveCat(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCatById(int id) throws ApiError {
        Optional<Category> catById = categoryRepository.findById(id);
        if (catById.isPresent()) {
            categoryRepository.deleteById(id);
        } else {
            throw new ApiError(HttpStatus.NOT_FOUND, 404, LocalDateTime.now(), "The requested resource does not exist");
        }
    }
}
