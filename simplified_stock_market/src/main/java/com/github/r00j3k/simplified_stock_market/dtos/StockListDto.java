package com.github.r00j3k.simplified_stock_market.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record StockListDto(
    @NotBlank
    @Size(max = 255)
    @JsonProperty("name")
    String stockName,

    @NotNull
    @PositiveOrZero
    Long quantity
) {}
