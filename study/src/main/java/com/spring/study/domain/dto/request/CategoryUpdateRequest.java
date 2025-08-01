package com.spring.study.domain.dto.request;

public record CategoryUpdateRequest(
        String name,
        String color,
        String imageUrl,
        String description
) {
}
