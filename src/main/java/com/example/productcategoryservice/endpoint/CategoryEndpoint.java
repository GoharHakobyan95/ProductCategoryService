package com.example.productcategoryservice.endpoint;


import com.example.productcategoryservice.dto.CategoryResponseDto;
import com.example.productcategoryservice.dto.CreateCategoryDto;
import com.example.productcategoryservice.dto.UpdateCategoryDto;
import com.example.productcategoryservice.entity.Category;
import com.example.productcategoryservice.exception.ApiError;
import com.example.productcategoryservice.exception.BaseException;
import com.example.productcategoryservice.mapper.CategoryMapper;
import com.example.productcategoryservice.security.CurrentUser;
import com.example.productcategoryservice.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RequestMapping("/api/categories")
@RestController
public class CategoryEndpoint {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    Logger log = LoggerFactory.getLogger(CategoryEndpoint.class.getName());

    @Operation(
            operationId = "getAllCategories",
            summary = "Get all categories",
            description = "Get all categories description"

    )
    @GetMapping
    public ResponseEntity<?> getAllCategories(@AuthenticationPrincipal CurrentUser currentUser) {
        log.info("Endpoint categories called by {}", currentUser.getUser().getEmail());
        return ResponseEntity.ok(categoryMapper.map(categoryService.getAllCats()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable("id") int id) throws BaseException {
        try {
            return ResponseEntity.ok(categoryMapper.map(categoryService.findCatById(id)));
        } catch (BaseException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found", exc);
        }
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        return ResponseEntity.ok(categoryService.saveCat(categoryMapper.map(createCategoryDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto,
                                                   @PathVariable("id") int id) {
        return ResponseEntity.ok((categoryService.saveCat(categoryMapper.map(updateCategoryDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") int id) throws ApiError {
        try {
            categoryService.deleteCatById(id);
            return ResponseEntity.ok(categoryMapper.map(categoryService.getAllCats()));
        } catch (ResponseStatusException exc) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists", exc);
        }
    }
}
