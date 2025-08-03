package com.spring.study.repository;

import com.spring.study.controller.ProductOptionController;
import com.spring.study.domain.entity.Product;
import com.spring.study.domain.entity.ProductOption;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    List<ProductOption> findAllByProduct(Product product);
}
