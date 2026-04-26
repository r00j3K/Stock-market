package com.github.r00j3k.simplified_stock_market.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuditLogDto(
    @JsonProperty("type")
    String transactionType,

    @JsonProperty("wallet_id")
    String walletId,

    @JsonProperty("stock_name")
    String stockName
) {}
