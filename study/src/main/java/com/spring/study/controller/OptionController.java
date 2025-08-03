package com.spring.study.controller;

import com.spring.study.domain.dto.request.OptionCreateRequest;
import com.spring.study.domain.dto.request.OptionUpdateRequest;
import com.spring.study.domain.dto.response.OptionDetailResponse;
import com.spring.study.domain.dto.response.OptionsResponse;
import com.spring.study.service.OptionService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/options")
public class OptionController {

    private final OptionService optionService;

    @GetMapping
    public ResponseEntity<OptionsResponse> getOptions(){
        return new ResponseEntity<>(optionService.getOptions(), HttpStatus.OK);
    }

    @GetMapping("{optionId}")
    public ResponseEntity<OptionDetailResponse> getDetailOption(
            @PathVariable("optionId") Long optionId
    ){
        return new ResponseEntity<>(optionService.getOptionDetail(optionId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OptionDetailResponse> createOption(
            @RequestBody OptionCreateRequest request
    ){
        return new ResponseEntity<>(optionService.createOption(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{optionId}")
    public ResponseEntity<OptionDetailResponse> updateOption(
            @PathVariable("optionId") Long optionId,
            @RequestBody OptionUpdateRequest request){
        return new ResponseEntity<>(optionService.updateOption(optionId, request), HttpStatus.OK);
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<Void> deleteOption(
            @PathVariable("optionId") Long optionId){
        optionService.deleteOption(optionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
