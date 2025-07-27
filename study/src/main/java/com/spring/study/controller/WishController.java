package com.spring.study.controller;

import com.spring.study.domain.dto.request.AddWishRequest;
import com.spring.study.domain.dto.request.DeleteWishRequest;
import com.spring.study.domain.dto.response.WishesResponse;
import com.spring.study.domain.entity.Wish;
import com.spring.study.service.WishService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wishes")
public class WishController {

    private final WishService wishService;

    // 장바구니 조회
    @GetMapping
    public ResponseEntity<WishesResponse> getWishes(){
        return new ResponseEntity<>(wishService.getWishes(), HttpStatus.OK);
    }

    // 장바구니 상품 추가
    @PostMapping
    public ResponseEntity<Wish> addWish(@RequestBody AddWishRequest request){
        return new ResponseEntity<>(wishService.addWish(request), HttpStatus.CREATED);
    }

    // 장바구니 상품 제거
    @DeleteMapping
    public ResponseEntity<Void> deleteWish(@RequestBody DeleteWishRequest request){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 장바구니 상품 수량 제어
    @PatchMapping("/{wishId}/increase")
    public ResponseEntity<Void> increaseQuantity(@PathVariable("wishId") Long wishId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{wishId}/decrease")
    public ResponseEntity<Void> decreaseQuantity(@PathVariable("wishId") Long wishId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
