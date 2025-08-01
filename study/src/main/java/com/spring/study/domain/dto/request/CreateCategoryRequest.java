package com.spring.study.domain.dto.request;

public record CreateCategoryRequest(
        String name,
        String color,
        String imageUrl,
        String description
) {
}
