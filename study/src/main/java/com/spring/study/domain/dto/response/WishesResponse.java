package com.spring.study.domain.dto.response;

import com.spring.study.domain.entity.Wish;
import java.util.List;
import org.springframework.data.domain.Page;

public record WishesResponse (Page<WishDetailResponse> wishes) {

    public static WishesResponse of(Page<Wish> wishes){

        Page<WishDetailResponse> wishDetailResponses = wishes.map(WishDetailResponse::from);

        return new WishesResponse(wishDetailResponses);
    }
}
