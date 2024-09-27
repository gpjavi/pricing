package com.pricing.infrastructure.rest.api.price;

import com.pricing.domain.exception.price.PriceFinderException;
import com.pricing.infrastructure.rest.api.error.ErrorResponseWebDto;
import com.pricing.infrastructure.rest.api.price.dto.PriceResponseRestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/v1/prices")
@Tag(name = "Prices", description = "Prices management")
public interface PriceApi {

  @Operation(
      operationId = "GetPriceApplied",
      summary = "Get price applied",
      description = "Get price applied given productId, brandId and date to apply",
      tags = {"Prices"},
      responses = {
          @ApiResponse(responseCode = "200", description = "Price applied", content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = PriceResponseRestDto.class))
          }),
          @ApiResponse(responseCode = "400", description = "Invalid parameters", content = {
              @Content(schema = @Schema(implementation = ErrorResponseWebDto.class))
          }),
          @ApiResponse(responseCode = "501", description = "Repository exception", content = {
              @Content(schema = @Schema(implementation = ErrorResponseWebDto.class))
          }),
          @ApiResponse(responseCode = "503", description = "Internal Server Error", content = {
              @Content(schema = @Schema(implementation = ErrorResponseWebDto.class))
          })
      }
  )
  @GetMapping(value = "/applied", produces = {"application/json"})
  ResponseEntity<PriceResponseRestDto> getPriceApplied(
      @RequestParam("date") LocalDateTime date,
      @RequestParam("productId") Long productId,
      @RequestParam("brandId") Long brandId) throws PriceFinderException;
}
