package com.spring.study.controller;

import com.spring.study.domain.dto.request.ProductOptionCreateRequest;
import com.spring.study.domain.dto.request.ProductOptionUpdateRequest;
import com.spring.study.domain.dto.response.ProductOptionDetailResponse;
import com.spring.study.domain.dto.response.ProductOptionResponse;
import com.spring.study.service.ProductOptionService;
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
@RequestMapping("/api/product-options")
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    // 특정 상품의 옵션 추가
    @PostMapping
    public ResponseEntity<ProductOptionDetailResponse> createProductOption(@RequestBody ProductOptionCreateRequest request){
        return new ResponseEntity<>
                (productOptionService.createProductOption(request), HttpStatus.CREATED);
    }

    // 특정 상품 옵션 전체 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductOptionResponse> getProductOptions(@PathVariable("productId") Long productId){
        return new ResponseEntity<>(productOptionService.getProductOptions(productId), HttpStatus.OK);
    }

    // 상품 옵션 수정
    @PatchMapping("/{productOptionId}")
    public ResponseEntity<ProductOptionDetailResponse> updateProductOption(
            @PathVariable("productOptionId") Long productOptionId,
            @RequestBody ProductOptionUpdateRequest request){
        return new ResponseEntity<>
                (productOptionService.updateProductOption(productOptionId, request), HttpStatus.OK);
    }

    // 상품 옵션 삭제
    @DeleteMapping("/{productOptionId}")
    public ResponseEntity<Void> deleteProductOption(
            @PathVariable("productOptionId") Long productOptionId){
        productOptionService.deleteProductOption(productOptionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 상품 수량 차감
    @PostMapping("/{productOptionId}")
    public ResponseEntity<Void> decreaseProductOption(
            @PathVariable("productOptionId") Long productOptionId,
            @RequestBody int quantity
            ){
        productOptionService.decreaseProductOptionQuantity(productOptionId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
