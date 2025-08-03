package com.spring.study.viewController;

import com.spring.study.controller.ProductController;
import com.spring.study.domain.dto.response.ProductResponse;
import com.spring.study.domain.entity.Product;
import com.spring.study.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductViewController {

    private final ProductController productController;
    private final ProductService productService;

    @GetMapping
    public String productList(@PageableDefault(size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
                              Model model) {
        Page<ProductResponse> products = productService.getProducts(pageable);
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/add")
    public String addProductForm(){
        return "product_form";
    }

    @GetMapping("/edit/{id}")
    public String editProductFrom(@PathVariable("id") Long id, Model model){
        Product product = productService.getProduct(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "product_edit_form";
        }
        return "redirect:/products";
    }
}
