package com.github.r00j3k.simplified_stock_market.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.r00j3k.simplified_stock_market.enums.TransactionType;

public record TradeRequestDto(
    @JsonProperty("type")
    TransactionType transactionType
) {}
