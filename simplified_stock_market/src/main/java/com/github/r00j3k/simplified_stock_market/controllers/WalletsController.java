package com.github.r00j3k.simplified_stock_market.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.r00j3k.simplified_stock_market.dtos.TradeRequestDto;
import com.github.r00j3k.simplified_stock_market.dtos.WalletStocksResponseDto;
import com.github.r00j3k.simplified_stock_market.services.TradeService;
import com.github.r00j3k.simplified_stock_market.services.WalletService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletsController {
    private final WalletService walletService;
    private final TradeService tradeService;

    @PostMapping("/{wallet_id}/stocks/{stock_name}")
    public ResponseEntity<Void> trade(
        @PathVariable("wallet_id") String walletId,
        @PathVariable("stock_name") String stockName,
        @Valid @RequestBody TradeRequestDto request
    ){
        tradeService.trade(walletId, stockName, request.transactionType());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{wallet_id}")
    public ResponseEntity<WalletStocksResponseDto> getWalletStocks(
        @PathVariable("wallet_id") String walletId
    ){
        return ResponseEntity.ok(walletService.getWalletStocks(walletId));
    }

    @GetMapping("/{wallet_id}/stocks/{stock_name}")
    public ResponseEntity<Long> getWalletStockQuantity(
        @PathVariable("wallet_id") String walletId,
        @PathVariable("stock_name") String stockName
    ){
        return ResponseEntity.ok(walletService.getStockQuantity(walletId, stockName));
    }
    
}
