package com.github.r00j3k.simplified_stock_market.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.r00j3k.simplified_stock_market.dtos.BankStocksDto;
import com.github.r00j3k.simplified_stock_market.services.BankStockService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class BankStocksController {
    private final BankStockService bankStockService;

    @GetMapping()
    public ResponseEntity<BankStocksDto> getBankStocks() {
        return ResponseEntity.ok(bankStockService.getBankStocks());
    }
    
}
