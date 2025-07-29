package com.spring.study.domain.dto.response;

import com.spring.study.domain.entity.Wish;

public record WishDetailResponse(String productName, int productPrice) {

    public static WishDetailResponse from (Wish wish){
        return new WishDetailResponse(wish.getProduct().getName(), wish.getProduct().getPrice());
    }
}
