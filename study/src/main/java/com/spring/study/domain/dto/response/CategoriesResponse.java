package com.spring.study.domain.dto.response;

import com.spring.study.domain.entity.Category;
import java.util.List;

public record CategoriesResponse(
        List<CategoryResponse> categoryResponses
) {

    public static CategoriesResponse of(List<Category> categories) {
        List<CategoryResponse> categoryResponseList = categories.stream()
                .map(CategoryResponse::from)
                .toList();
        return new CategoriesResponse(categoryResponseList);
    }
}
