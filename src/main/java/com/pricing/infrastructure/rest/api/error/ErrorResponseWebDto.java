package com.pricing.infrastructure.rest.api.error;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("ErrorResponseWebDto")
@Schema(name = "ErrorResponseWebDto", description = "Error response object")
public class ErrorResponseWebDto {

  @Schema(description = "Error code", example = "104", implementation = Integer.class)
  private Integer code;

  @Schema(description = "Error message", example = "Wrong configuration", implementation = String.class)
  private String message;

  @Schema(description = "Detailed error description", example = "Missing request information object", implementation = String.class)
  private String description;

}
