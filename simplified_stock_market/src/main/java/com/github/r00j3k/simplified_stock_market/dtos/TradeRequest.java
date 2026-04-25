package com.github.r00j3k.simplified_stock_market.dtos;

import jakarta.validation.constraints.Pattern;

public record TradeRequest(
    @Pattern(regexp = "(?i)BUY|SELL", message = "Type must be BUY or SELL")
    String type
) {}
