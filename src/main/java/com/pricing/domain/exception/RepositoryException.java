package com.pricing.domain.exception;

import lombok.Getter;

@Getter
public class RepositoryException extends Exception {


  public RepositoryException(Throwable throwable, String message) {
    super(message, throwable);
  }


}
