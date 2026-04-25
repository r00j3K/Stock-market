package com.github.r00j3k.simplified_stock_market.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.r00j3k.simplified_stock_market.dtos.TradeRequest;
import com.github.r00j3k.simplified_stock_market.services.WalletService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/{walletId}/stocks/{stockName}")
    public ResponseEntity<Void> trade(
        @PathVariable String walletId,
        @PathVariable String stockName,
        @Valid @RequestBody TradeRequest request
    ){
        walletService.trade(walletId, stockName, request.type());
        return ResponseEntity.ok().build();
    }
}
