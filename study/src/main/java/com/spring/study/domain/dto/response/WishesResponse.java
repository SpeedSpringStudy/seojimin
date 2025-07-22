package com.spring.study.domain.dto.response;

import com.spring.study.domain.entity.Wish;
import java.util.List;

public record WishesResponse (List<Wish> wishes) {}
