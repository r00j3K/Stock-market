package com.github.r00j3k.simplified_stock_market.dtos;

import java.util.List;

public record WalletStocksResponseDto(
    String id,
    List<StockListDto> stocks
) {}
