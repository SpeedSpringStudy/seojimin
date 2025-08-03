package com.spring.study.domain.dto.response;

import com.spring.study.domain.entity.ProductOption;
import lombok.Builder;

@Builder
public record ProductOptionDetailResponse(
        Long id,
        String productName,
        String optionName,
        int quantity
) {
    public static ProductOptionDetailResponse from(ProductOption productOption){
        return ProductOptionDetailResponse.builder()
                .id(productOption.getId())
                .productName(productOption.getProduct().getName())
                .optionName(productOption.getOption().getOptionName())
                .quantity(productOption.getQuantity())
                .build();
    }
}
