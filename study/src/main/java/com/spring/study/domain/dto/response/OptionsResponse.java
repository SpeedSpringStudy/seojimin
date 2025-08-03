package com.spring.study.domain.dto.response;

import com.spring.study.domain.entity.Option;
import java.util.List;

public record OptionsResponse(List<Option> optionList) {
    public static OptionsResponse from(List<Option> optionList) {
        return new OptionsResponse(optionList);
    }
}
