package com.pricing.domain.exception.price;

import com.pricing.domain.exception.DomainException;
import com.pricing.domain.exception.ErrorCode;
import com.pricing.domain.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class PriceFinderException extends DomainException {

  private final PriceErrorCode priceErrorCode;

  public PriceFinderException(String message) {
    super(message, PriceErrorCode.UNKNOWN_EXCEPTION.getErrorCode());
    this.priceErrorCode = PriceErrorCode.UNKNOWN_EXCEPTION;
  }

  public PriceFinderException(String message, PriceErrorCode priceErrorCode) {
    super(message, priceErrorCode.getErrorCode());
    this.priceErrorCode = priceErrorCode;
  }

  public PriceFinderException(Throwable throwable, String message,
      PriceErrorCode priceErrorCode) {
    super(throwable, message, priceErrorCode.getErrorCode());
    this.priceErrorCode = priceErrorCode;
  }

  @Getter
  @AllArgsConstructor
  public enum PriceErrorCode {

    REPOSITORY_EXCEPTION(100, "Unable to perform required operation in repository",
        ExceptionType.REPOSITORY),
    INVALID_DATA(101, "Specified data is not valid", ExceptionType.RULES_VIOLATION),
    UNKNOWN_EXCEPTION(700, "Unable to perform required operation", ExceptionType.UNKNOWN),
    NOT_FOUND(404, "Not found", ExceptionType.NOT_FOUND);

    private final ErrorCode errorCode;


    PriceErrorCode(int code, String message, ExceptionType exceptionType) {
      this.errorCode = new ErrorCode(code, message, exceptionType);
    }
  }
}
