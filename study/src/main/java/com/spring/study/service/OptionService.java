package com.spring.study.service;

import com.spring.study.common.exception.ErrorCode;
import com.spring.study.common.exception.custonException.NotFoundException;
import com.spring.study.domain.dto.request.OptionCreateRequest;
import com.spring.study.domain.dto.request.OptionUpdateRequest;
import com.spring.study.domain.dto.response.OptionDetailResponse;
import com.spring.study.domain.dto.response.OptionsResponse;
import com.spring.study.domain.entity.Option;
import com.spring.study.repository.OptionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionsResponse getOptions() {
        List<Option> optionList = optionRepository.findAll();
        return OptionsResponse.from(optionList);
    }

    public Option getOption(Long optionId){
        return optionRepository.findById(optionId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.OPTION_NOT_FOUND));
    }

    public OptionDetailResponse getOptionDetail(Long optionId) {
        Option target = optionRepository.findById(optionId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.OPTION_NOT_FOUND));
        return OptionDetailResponse.from(target);
    }

    @Transactional
    public OptionDetailResponse createOption(OptionCreateRequest request) {
        Option newOption = new Option(request.optionName());
        return OptionDetailResponse.from(optionRepository.save(newOption));
    }

    @Transactional
    public OptionDetailResponse updateOption(Long optionId, OptionUpdateRequest request) {
        Option target = optionRepository.findById(optionId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.OPTION_NOT_FOUND));
        return OptionDetailResponse.from(target.update(request.OptionName()));
    }

    @Transactional
    public void deleteOption(Long optionId) {
        optionRepository.deleteById(optionId);
    }
}
