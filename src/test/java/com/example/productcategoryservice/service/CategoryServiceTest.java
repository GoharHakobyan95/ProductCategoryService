package com.example.productcategoryservice.service;

import com.example.productcategoryservice.entity.Category;
import com.example.productcategoryservice.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    private Category category;

    @BeforeEach
    public void category() {
        category = Category.builder()
                .name("Items")
                .build();
    }

    @Test
    void getAllCats() {
        Category category2 = Category.builder()
                .name("Items")
                .build();
        when(categoryRepository.findAll()).thenReturn(List.of(category, category2));
        List<Category> allCats = categoryService.getAllCats();
        assertThat(allCats).isNotNull();
        assertThat(allCats.size()).isEqualTo(2);
    }

    @Test
    void findCatById() {
        Mockito.when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        assertThat(categoryService.findCatById(1)).isEqualTo(category);
//        verify(categoryRepository, times(1)).findById(any(category.getId())));
    }

    @Test
    void saveCat() {
        Mockito.when(categoryRepository.save(any())).thenReturn(category);

        categoryService.saveCat(Category.builder()
                .name("Mobile")
                .build());

        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    void deleteCatById() {
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        categoryService.deleteCatById(category.getId());
        verify(categoryRepository).deleteById(category.getId());

    }
}