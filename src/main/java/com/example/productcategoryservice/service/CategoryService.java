package com.example.productcategoryservice.service;

import com.example.productcategoryservice.entity.Category;
import com.example.productcategoryservice.exception.Error;
import com.example.productcategoryservice.exception.EntityNotFoundException;
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

    public Category findCatById(int id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException(Error.DIRECTORY_NOT_FOUND);
        }
        return categoryRepository.findById(id).get();
    }

    public Category saveCat(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCatById(int id){
        Optional<Category> catById = categoryRepository.findById(id);
        if (catById.isPresent()) {
            categoryRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(Error.DIRECTORY_NOT_FOUND);
        }
    }
}
