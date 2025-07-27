package com.spring.study.domain.dto.response;

import com.spring.study.domain.entity.Wish;
import java.util.List;

public record WishesResponse (List<WishDetailResponse> wishes) {

    public static WishesResponse of(List<Wish> wishes){

        List<WishDetailResponse> wishDetailResponses = wishes.stream()
                .map(WishDetailResponse::from)
                .toList();

        return new WishesResponse(wishDetailResponses);
    }
}
