package com.spring.study.controller;

import com.spring.study.domain.dto.request.CategoryUpdateRequest;
import com.spring.study.domain.dto.request.CreateCategoryRequest;
import com.spring.study.domain.dto.response.CategoriesResponse;
import com.spring.study.domain.dto.response.CategoryResponse;
import com.spring.study.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoriesResponse> getCategories(){
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestBody CreateCategoryRequest request
    ){
        return new ResponseEntity<>(categoryService.createCategory(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody CategoryUpdateRequest request){
        return new ResponseEntity<>(categoryService.updateCategory(categoryId, request), HttpStatus.OK);
    }
    
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable("categoryId") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
