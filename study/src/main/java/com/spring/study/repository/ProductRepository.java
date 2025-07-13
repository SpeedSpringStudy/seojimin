package com.spring.study.repository;

import com.spring.study.domain.Product;
import java.util.List;

public interface ProductRepository {

    Product save(String name, int price);
    Product update(Long id, String name, int price);
    List<Product> findProducts();
    Product findProduct(Long id);
    void delete(Long id);
}
