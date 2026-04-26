package com.github.r00j3k.simplified_stock_market.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.github.r00j3k.simplified_stock_market.entities.WalletStockId;
import com.github.r00j3k.simplified_stock_market.exceptions.WalletNotFoundException;
import com.github.r00j3k.simplified_stock_market.repositories.WalletRepository;
import com.github.r00j3k.simplified_stock_market.repositories.WalletStockRepository;
import com.github.r00j3k.simplified_stock_market.dtos.StockList;
import com.github.r00j3k.simplified_stock_market.dtos.WalletStocksResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletStockRepository walletStockRepository;
    private final WalletRepository walletRepository;

    public WalletStocksResponse getWalletStocks(String walletId){
        if(!walletRepository.existsById(walletId)){
            throw new WalletNotFoundException("Wallet not found.");
        }

        var walletStocks = walletStockRepository.findAllByWalletStockIdWalletId(walletId);

        List<StockList> stockList = walletStocks.stream()
            .map(ws -> new StockList(
                ws.getWalletStockId().getStockName(),
                ws.getQuantity()
            ))
            .toList();
        
        return new WalletStocksResponse(walletId, stockList);
    }

    public Long getStockQuantity(String walletId, String stockName){
        if(!walletRepository.existsById(walletId)){
            throw new WalletNotFoundException("Wallet not found.");
        }

        return walletStockRepository.findById(new WalletStockId(walletId, stockName))
            .map(ws -> ws.getQuantity())
            .orElse(0L);
    }
}
