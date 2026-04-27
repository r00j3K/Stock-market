package com.github.r00j3k.simplified_stock_market.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TransactionType {
    BUY, SELL;

    @JsonCreator
    public static TransactionType from(String type) {
        return TransactionType.valueOf(type.toUpperCase());
    }
}
