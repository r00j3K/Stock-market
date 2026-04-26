package com.github.r00j3k.simplified_stock_market.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Pattern;

public record TradeRequestDto(
    @Pattern(regexp = "(?i)BUY|SELL", message = "Type must be BUY or SELL case insensitive")
    @JsonProperty("type")
    String transactionType
) {}
