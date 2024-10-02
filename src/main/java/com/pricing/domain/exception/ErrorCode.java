package com.pricing.domain.exception;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorCode implements Serializable {


  @Serial
  private static final long serialVersionUID = 6514028596187662552L;
  private Integer id;
  private String message;
  private ExceptionType exceptionType;


}
