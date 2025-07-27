package com.spring.study.service;

import com.spring.study.common.exception.ErrorCode;
import com.spring.study.common.exception.custonException.NotFoundException;
import com.spring.study.domain.dto.request.ProductRequest;
import com.spring.study.domain.entity.Product;
import com.spring.study.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(Long id){
        return productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Transactional
    public Product addProduct(ProductRequest request){
        Product newProduct = Product.builder()
                .name(request.name())
                .price(request.price())
                .build();
        return productRepository.save(newProduct);
    }

    @Transactional
    public Product updateProduct(Long id, ProductRequest request){
        Product targetProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        targetProduct.update(request.name(), request.price());
        return targetProduct;
    }

    @Transactional
    public void delete(Long id){
        productRepository.deleteById(id);
    }
}
