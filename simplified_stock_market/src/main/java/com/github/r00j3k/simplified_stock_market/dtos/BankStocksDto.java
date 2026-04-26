package com.github.r00j3k.simplified_stock_market.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record BankStocksDto(
    @NotNull
    @Valid
    @JsonProperty("stocks")
    List<StockListDto> bankStocks
) {}

