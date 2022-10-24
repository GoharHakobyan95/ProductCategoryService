package com.example.productcategoryservice.endpoint;

import com.example.productcategoryservice.dto.CategoryResponseDto;
import com.example.productcategoryservice.dto.CreateCategoryDto;
import com.example.productcategoryservice.dto.UpdateCategoryDto;
import com.example.productcategoryservice.entity.Category;
import com.example.productcategoryservice.mapper.CategoryMapper;
import com.example.productcategoryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class CategoryEndpoint {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/categories")
    public List<CategoryResponseDto> getAllCategories() {
        return categoryMapper.map(categoryService.getAllCats());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") int id) {
        Optional<Category> byId = categoryService.findCatById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(byId.get());
    }

    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        return ResponseEntity.ok(categoryService.saveCat(categoryMapper.map(createCategoryDto)));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto,
    @PathVariable ("id") int id) {
        Optional<Category> catById = categoryService.findCatById(id);
        if(catById.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok((categoryService.saveCat(categoryMapper.map(updateCategoryDto))));
    }


    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") int id) {
        Optional<Category> catById = categoryService.findCatById(id);
        if (catById.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            categoryService.deleteCatById(id);
            return ResponseEntity.ok(categoryMapper.map(categoryService.getAllCats()));
        }
    }
}