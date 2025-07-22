package com.spring.study.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Wish {

    private Long id;
    private Long memberId;
    private Long productId;
    private int quantity;

    public int addQuantity(){
        return ++this.quantity;
    }

    public int minusQuantity(){
        return --this.quantity;
    }
}
