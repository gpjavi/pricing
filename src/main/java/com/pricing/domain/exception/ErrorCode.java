package com.pricing.domain.exception;

public record ErrorCode(Integer id, String message, ExceptionType exceptionType) {

}
