package com.github.r00j3k.simplified_stock_market.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.r00j3k.simplified_stock_market.dtos.BankStocksResponse;
import com.github.r00j3k.simplified_stock_market.dtos.StockList;
import com.github.r00j3k.simplified_stock_market.repositories.BankStockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankStockService {
    private final BankStockRepository bankStockRepository;

    public BankStocksResponse getBankStocks(){
        List<StockList> stocks = bankStockRepository.findAll()
            .stream()
            .map(bs -> new StockList(
                    bs.getStockName(),
                    bs.getQuantity()
            ))
            .toList();
        
        return new BankStocksResponse(stocks);
    }
}
