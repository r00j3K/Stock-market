package com.github.r00j3k.simplified_stock_market.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.github.r00j3k.simplified_stock_market.entities.WalletStockId;
import com.github.r00j3k.simplified_stock_market.exceptions.WalletNotFoundException;
import com.github.r00j3k.simplified_stock_market.repositories.WalletRepository;
import com.github.r00j3k.simplified_stock_market.repositories.WalletStockRepository;
import com.github.r00j3k.simplified_stock_market.dtos.StockListDto;
import com.github.r00j3k.simplified_stock_market.dtos.WalletStocksResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletStockRepository walletStockRepository;
    private final WalletRepository walletRepository;

    public WalletStocksResponseDto getWalletStocks(String walletId){
        if(!walletRepository.existsById(walletId)){
            throw new WalletNotFoundException("Wallet not found.");
        }

        List<StockListDto> stockList =  walletStockRepository.findAllByWalletStockIdWalletId(walletId)
        .stream()
            .map(ws -> new StockListDto(
                ws.getWalletStockId().getStockName(),
                ws.getQuantity()
            ))
            .toList();
        
        return new WalletStocksResponseDto(walletId, stockList);
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
