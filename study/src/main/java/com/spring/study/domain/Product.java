package com.spring.study.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    public Long id;
    public String name;
    public int price;

    public void update(String name, int price){
        if (!this.name.equals(name)) this.name = name;
        if (this.price != price) this.price = price;
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
