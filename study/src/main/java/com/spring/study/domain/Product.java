package com.spring.study.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
}
