package com.pricing.infrastructure.rest.spring;


import com.pricing.domain.exception.DomainException;
import com.pricing.domain.exception.ExceptionType;
import com.pricing.infrastructure.rest.api.error.ErrorResponseWebDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseWebDto> handleException(Exception ex,
      HttpServletRequest request) {
    log.error("Handling Exception", ex);

    ErrorResponseWebDto responseWebDto = ErrorResponseWebDto.builder()
        .message(ex.getLocalizedMessage()).build();
    return new ResponseEntity<>(responseWebDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(DomainException.class)
  public ResponseEntity<ErrorResponseWebDto> handleDomainException(
      DomainException domainException) {
    log.error("Handling DomainException: ", domainException);
    ErrorResponseWebDto responseWebDto =
        ErrorResponseWebDto.builder().message(domainException.getMessage())
            .code(domainException.getErrorCodeId()).description(
                domainException.getErrorCodeDescription()).build();

    HttpStatus status = retrieveStatusFromDomainException(domainException.getExceptionType());
    return new ResponseEntity<>(responseWebDto, status);

  }

  private HttpStatus retrieveStatusFromDomainException(ExceptionType exceptionType) {

    return switch (exceptionType) {
      case ExceptionType.REPOSITORY -> HttpStatus.SERVICE_UNAVAILABLE;
      case ExceptionType.RULES_VIOLATION -> HttpStatus.BAD_REQUEST;
      case ExceptionType.NOT_FOUND ->  HttpStatus.NOT_FOUND;
      default -> HttpStatus.INTERNAL_SERVER_ERROR;
    };
  }

}
