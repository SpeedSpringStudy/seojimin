package com.spring.study.service;

import com.spring.study.common.exception.ErrorCode;
import com.spring.study.common.exception.custonException.NotFoundException;
import com.spring.study.domain.dto.request.OptionUpdateRequest;
import com.spring.study.domain.dto.request.ProductOptionCreateRequest;
import com.spring.study.domain.dto.request.ProductOptionUpdateRequest;
import com.spring.study.domain.dto.response.ProductOptionDetailResponse;
import com.spring.study.domain.dto.response.ProductOptionResponse;
import com.spring.study.domain.entity.Option;
import com.spring.study.domain.entity.Product;
import com.spring.study.domain.entity.ProductOption;
import com.spring.study.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductService productService;
    private final OptionService optionService;
    private final ProductOptionRepository repository;

    public ProductOptionResponse getProductOptions(Long productId) {
        Product product = productService.getProduct(productId);
        return ProductOptionResponse.from(repository.findAllByProduct(product));
    }

    @Transactional
    public ProductOptionDetailResponse createProductOption(ProductOptionCreateRequest request) {
        Product targetProduct = productService.getProduct(request.productId());
        Option targetOption = optionService.getOption(request.optionId());
        ProductOption newProductOption = ProductOption.builder()
                .product(targetProduct)
                .option(targetOption)
                .build();
        return ProductOptionDetailResponse.from(repository.save(newProductOption));
    }

    @Transactional
    public ProductOptionDetailResponse updateProductOption(Long productOptionId, ProductOptionUpdateRequest request) {
        ProductOption productOption = repository.findById(productOptionId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_OPTION_NOT_FOUND));

        return ProductOptionDetailResponse.from(productOption.updateQuantity(request.quantity()));
    }

    @Transactional
    public void deleteProdcutOption(Long productOptionId) {
        repository.deleteById(productOptionId);
    }
}
