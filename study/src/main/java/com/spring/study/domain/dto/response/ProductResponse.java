package com.spring.study.domain.dto.response;

import com.spring.study.domain.entity.Product;

public record ProductResponse (Long id, String name, int price, CategoryResponse category) {

    public static ProductResponse from(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                CategoryResponse.from(product.getCategory()));
    }
}
