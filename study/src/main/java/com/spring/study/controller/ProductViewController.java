package com.spring.study.controller;

import com.spring.study.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductViewController {

    private final ProductController pc;

    @GetMapping
    public String productList(Model model){
        model.addAttribute("products",pc.getProducts());
        return "products";
    }

    @GetMapping("/add")
    public String addProductForm(){
        return "product_form";
    }

    @GetMapping("/edit/{id}")
    public String editProductFrom(@PathVariable("id") Long id, Model model){
        Product product = pc.getProducts().stream()
                .filter(p -> p.id.equals(id))
                .findFirst()
                .orElse(null);
        if (product != null) {
            model.addAttribute("product", product);
            return "product_edit_form";
        }
        return "redirect:/products";
    }
}
