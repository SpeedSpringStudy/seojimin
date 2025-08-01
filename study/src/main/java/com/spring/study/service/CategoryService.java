package com.spring.study.service;

import com.spring.study.common.exception.ErrorCode;
import com.spring.study.common.exception.custonException.NotFoundException;
import com.spring.study.domain.dto.request.CategoryUpdateRequest;
import com.spring.study.domain.dto.request.CreateCategoryRequest;
import com.spring.study.domain.dto.response.CategoriesResponse;
import com.spring.study.domain.dto.response.CategoryResponse;
import com.spring.study.domain.entity.Category;
import com.spring.study.repository.CategoryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoriesResponse getCategories() {
        return CategoriesResponse.of(categoryRepository.findAll());
    }

    @Transactional
    public CategoryResponse updateCategory(Long categoryId, CategoryUpdateRequest request) {
        Category target = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
        return CategoryResponse.from(target.update(request));
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category newCategory = Category.builder()
                .name(request.name())
                .color(request.color())
                .imageUrl(request.imageUrl())
                .description(request.description())
                .build();
        return CategoryResponse.from(categoryRepository.save(newCategory));
    }
}
