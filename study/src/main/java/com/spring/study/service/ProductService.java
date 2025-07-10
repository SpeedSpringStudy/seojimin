package com.spring.study.service;

import com.spring.study.domain.dto.ProductRequest;
import com.spring.study.domain.Product;
import com.spring.study.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProducts(){
        return productRepository.findProducts();
    }

    public Product addProduct(ProductRequest request){
        return productRepository.save(request.name(), request.price());
    }

    public Product updateProduct(Long id, ProductRequest request){
        return productRepository.update(id, request.name(), request.price());
    }

    public void delete(Long id){
        productRepository.delete(id);
    }
}
