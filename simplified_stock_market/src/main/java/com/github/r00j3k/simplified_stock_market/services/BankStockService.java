package com.github.r00j3k.simplified_stock_market.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.r00j3k.simplified_stock_market.dtos.BankStocksDto;
import com.github.r00j3k.simplified_stock_market.dtos.StockListDto;
import com.github.r00j3k.simplified_stock_market.repositories.BankStockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankStockService {
    private final BankStockRepository bankStockRepository;

    public BankStocksDto getBankStocks(){
        List<StockListDto> stocks = bankStockRepository.findAll()
            .stream()
            .map(bs -> new StockListDto(
                    bs.getStockName(),
                    bs.getQuantity()
            ))
            .toList();
        
        return new BankStocksDto(stocks);
    }
}
