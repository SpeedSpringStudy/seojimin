package com.spring.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductOptionRunner {
    @Autowired
    private ProductOptionService service;

    public void run(Long id, int qty) {
        service.decreaseProductOptionQuantity(id, qty);
    }
}
