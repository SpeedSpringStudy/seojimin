package com.spring.study.domain.dto.response;

import com.spring.study.domain.entity.ProductOption;
import java.util.List;

public record ProductOptionResponse(List<ProductOption> productOptions) {

    public static ProductOptionResponse from(List<ProductOption> productOptions){
        return new ProductOptionResponse(productOptions);
    }
}
