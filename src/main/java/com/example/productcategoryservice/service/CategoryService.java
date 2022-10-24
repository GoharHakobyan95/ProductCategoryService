package com.example.productcategoryservice.service;

import com.example.productcategoryservice.entity.Category;
import com.example.productcategoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCats() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findCatById(int id) {
        return categoryRepository.findById(id);
    }

    public Category saveCat(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCatById(int id) {
        categoryRepository.deleteById(id);
    }
}
