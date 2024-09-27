package com.pricing.domain.exception;

import lombok.Getter;

@Getter
public abstract class DomainException extends Exception {

  private final ErrorCode errorCode;

  protected DomainException(Throwable throwable, String message, ErrorCode errorCode) {
    super(message, throwable);
    this.errorCode = errorCode;
  }

  protected DomainException(String message, ErrorCode errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public ExceptionType getExceptionType() {
    return errorCode.exceptionType();
  }

  public Integer getErrorCodeId() {
    return errorCode.id();
  }

  public String getErrorCodeDescription() {
    return errorCode.message();
  }
}
