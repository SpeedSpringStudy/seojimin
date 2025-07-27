package com.spring.study.common.exception.custonException;


import com.spring.study.common.exception.ErrorCode;

public class NotFoundException extends BusinessException {
  public NotFoundException(ErrorCode ec) {
    super(ec);
  }
}
