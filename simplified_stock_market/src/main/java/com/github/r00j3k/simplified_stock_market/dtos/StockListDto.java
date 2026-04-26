package com.github.r00j3k.simplified_stock_market.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record StockListDto(
    @NotBlank
    @Size(max = 255)
    String name,

    @NotNull
    @PositiveOrZero
    Long quantity
) {}
