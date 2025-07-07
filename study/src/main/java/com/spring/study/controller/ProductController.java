package com.spring.study.controller;

import com.spring.study.domain.dto.ProductRequest;
import com.spring.study.domain.Product;
import com.spring.study.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // 상품 목록 조회
    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    // 상품 추가
    @PostMapping
    public Product addProduct(@RequestBody ProductRequest request){
        return productService.addProduct(request);
    }

    // 상품 수정
    @PutMapping("{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest request){
        return productService.updateProduct(id, request);
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.delete(id);
    }
}
