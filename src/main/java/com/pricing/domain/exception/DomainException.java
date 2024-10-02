package com.pricing.domain.exception;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;

@Getter
public abstract class DomainException extends Exception implements Serializable {


  @Serial
  private static final long serialVersionUID = -7487603407185667716L;
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
    return errorCode.getExceptionType();
  }

  public Integer getErrorCodeId() {
    return errorCode.getId();
  }

  public String getErrorCodeDescription() {
    return errorCode.getMessage();
  }
}
