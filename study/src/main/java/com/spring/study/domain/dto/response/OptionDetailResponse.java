package com.spring.study.domain.dto.response;

import com.spring.study.domain.entity.Option;

public record OptionDetailResponse(Long id, String optionName) {

    public static OptionDetailResponse from(Option option){
        return new OptionDetailResponse(option.getId(), option.getOptionName());
    }
}
