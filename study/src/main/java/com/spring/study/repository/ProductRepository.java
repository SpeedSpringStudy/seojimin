package com.spring.study.repository;

import com.spring.study.domain.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private Map<Long, Product> products = new HashMap<>();
    private static long sequence = 0L;

    // 상품 추가
    public Product save(String name, int price){
        Product newProduct = new Product(++sequence, name, price);
        products.put(newProduct.getId(), newProduct);
        return newProduct;
    }

    // 상품 수정
    public Product update(Long id, String name, int price){
        Product target = products.get(id);
        target.update(name, price);
        return target;
    }

    // 상품 조회(목록)
    public List<Product> findProducts(){
        return new ArrayList<>(products.values());
    }

    // 상품 조회(개별)
    public Product findProduct(Long id){
        Product findProduct = products.get(id);
        return new Product(findProduct.getId(), findProduct.getName(), findProduct.getPrice());
    }

    // 상품 삭제
    public void delete(Long id){
        products.remove(id);
    }
}
