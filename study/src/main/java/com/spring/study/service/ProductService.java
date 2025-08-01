package com.spring.study.service;

import com.spring.study.common.exception.ErrorCode;
import com.spring.study.common.exception.custonException.NotFoundException;
import com.spring.study.domain.dto.request.ProductRequest;
import com.spring.study.domain.dto.response.ProductResponse;
import com.spring.study.domain.entity.Category;
import com.spring.study.domain.entity.Product;
import com.spring.study.repository.CategoryRepository;
import com.spring.study.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Page<ProductResponse> getProducts(Pageable pageable){
        return productRepository.findAll(pageable).map(ProductResponse::from);
    }

    public Product getProduct(Long id){
        return productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Transactional
    public Product addProduct(ProductRequest request){
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(()-> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

        Product newProduct = Product.builder()
                .name(request.name())
                .price(request.price())
                .category(category)
                .build();

        return productRepository.save(newProduct);
    }

    @Transactional
    public Product updateProduct(Long id, ProductRequest request){
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(()-> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

        Product targetProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        targetProduct.update(request.name(), request.price(), category);
        return targetProduct;
    }

    @Transactional
    public void delete(Long id){
        productRepository.deleteById(id);
    }
}
