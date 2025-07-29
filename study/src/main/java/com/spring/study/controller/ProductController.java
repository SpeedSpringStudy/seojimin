package com.spring.study.controller;

import com.spring.study.domain.dto.request.ProductRequest;
import com.spring.study.domain.dto.response.ProductResponse;
import com.spring.study.domain.entity.Product;
import com.spring.study.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // 상품 목록 조회
    @GetMapping
    public Page<ProductResponse> getProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return productService.getProducts(pageable);
    }

    @GetMapping("{id}")
    public Product getProduct(@PathVariable("id") Long id){
        return productService.getProduct(id);
    }

    // 상품 추가
    @PostMapping
    public Product addProduct(@Valid @RequestBody ProductRequest request){
        return productService.addProduct(request);
    }

    // 상품 수정
    @PutMapping("{id}")
    public Product updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductRequest request){
        return productService.updateProduct(id, request);
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.delete(id);
    }
}
