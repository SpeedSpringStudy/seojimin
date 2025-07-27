package com.spring.study.service;

import com.spring.study.common.exception.ErrorCode;
import com.spring.study.common.exception.custonException.NotFoundException;
import com.spring.study.domain.dto.request.AddWishRequest;
import com.spring.study.domain.dto.request.DeleteWishRequest;
import com.spring.study.domain.dto.response.WishesResponse;
import com.spring.study.domain.entity.Member;
import com.spring.study.domain.entity.Wish;
import com.spring.study.repository.WishRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;
    private final MemberService memberService;
    private final ProductService productService;

    // 장바구니 조회
    public WishesResponse getWishes() {
        Member member = memberService.getMemberByAuthentication();
        return WishesResponse.of(wishRepository.findAllByMember(member));
    }

    // 장바구니 상품 추가
    @Transactional
    public Wish addWish(AddWishRequest request) {
        Member member = memberService.getMemberByAuthentication();

        Wish newWish = Wish.builder()
                .member(member)
                .product(productService.getProduct(request.productId()))
                .build();

        return wishRepository.save(newWish);
    }

    // 장바구니 상품 제거
    @Transactional
    public void deleteWish(DeleteWishRequest request) {
        Member member = memberService.getMemberByAuthentication();
        wishRepository.deleteById(request.wishId());
    }

    // 장바구니 상품 수량 제어
    @Transactional
    public void increaseQuantity(Long wishId) {
        Member member = memberService.getMemberByAuthentication();
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.WISH_NOT_FOUND));

        wish.addQuantity();
    }

    @Transactional
    public void decreaseQuantity(Long wishId) {
        Member member = memberService.getMemberByAuthentication();
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.WISH_NOT_FOUND));
        wish.minusQuantity();
    }
}
