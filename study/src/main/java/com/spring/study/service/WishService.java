package com.spring.study.service;

import com.spring.study.domain.dto.request.AddWishRequest;
import com.spring.study.domain.dto.request.DeleteWishRequest;
import com.spring.study.domain.dto.response.WishesResponse;
import com.spring.study.domain.entity.Member;
import com.spring.study.domain.entity.Wish;
import com.spring.study.repository.dao.WishDao;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishService {

    private final WishDao wishDao;
    private final MemberService memberService;

    // 장바구니 조회
    public WishesResponse getWishes() {
        Member member = memberService.getMemberByAuthentication();
        return new WishesResponse(wishDao.findWishes(member.getId()));
    }

    // 장바구니 상품 추가
    public WishesResponse addWish(AddWishRequest request) {
        Member member = memberService.getMemberByAuthentication();
        List<Wish> wishes = wishDao.createWish(member.getId(), request.productId(), 1);
        return new WishesResponse(wishes);
    }

    // 장바구니 상품 제거
    public WishesResponse deleteWish(DeleteWishRequest request) {
        Member member = memberService.getMemberByAuthentication();
        return new WishesResponse(wishDao.delete(request.wishId(), member.id));
    }

    // 장바구니 상품 수량 제어
    public WishesResponse increaseQuantity(Long wishId) {
        Member member = memberService.getMemberByAuthentication();
        Wish wish = wishDao.findWish(wishId);
        return new WishesResponse(wishDao.updateQuantity(member.id, wish.getQuantity()+1, wishId));
    }

    public WishesResponse decreaseQuantity(Long wishId) {
        Member member = memberService.getMemberByAuthentication();
        Wish wish = wishDao.findWish(wishId);
        return new WishesResponse(wishDao.updateQuantity(member.id, wish.getQuantity()-1, wishId));
    }
}
