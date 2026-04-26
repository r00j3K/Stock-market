package com.github.r00j3k.simplified_stock_market.dtos;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record BankStocksDto(
    @NotNull
    @Valid
    List<StockListDto> stocks
) {}

