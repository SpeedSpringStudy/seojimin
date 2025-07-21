package com.spring.study.service;

import com.spring.study.domain.dto.request.ProductRequest;
import com.spring.study.domain.entity.Product;
import com.spring.study.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(){
        return productRepository.findProducts();
    }

    public Product getProduct(Long id){
        return productRepository.findProduct(id);
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
