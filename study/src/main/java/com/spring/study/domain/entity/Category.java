package com.spring.study.domain.entity;

import com.spring.study.domain.dto.request.CategoryUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;

    @Column(name = "image_url")
    private String imageUrl;
    private String description;

    public Category update(CategoryUpdateRequest request){
        this.name = request.name();
        this.color = request.color();
        this.imageUrl = request.imageUrl();
        this.description = request.description();
        return this;
    }
}
