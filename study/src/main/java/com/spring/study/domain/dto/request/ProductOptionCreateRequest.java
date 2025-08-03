package com.spring.study.domain.dto.request;

public record ProductOptionCreateRequest(
        Long productId,
        Long optionId,
        int quantity
){
}
