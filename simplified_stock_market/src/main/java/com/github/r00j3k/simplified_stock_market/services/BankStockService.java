package com.github.r00j3k.simplified_stock_market.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.r00j3k.simplified_stock_market.dtos.BankStocksDto;
import com.github.r00j3k.simplified_stock_market.dtos.StockListDto;
import com.github.r00j3k.simplified_stock_market.entities.BankStock;
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

    // No need for pessimistic lock here, because we are not performing 
    // a read-calculate-write operation, like in trade() method. 
    // delete/saveAll operations are atomic within a transaction 
    // and do not depend on the previous state of the records.
    @Transactional
    public void setBankStocks(BankStocksDto bankStocksDto){
        bankStockRepository.deleteAll();

        List<BankStock> bankStocks = bankStocksDto.stocks()
            .stream()
            .map(
                bs -> BankStock.builder()
                    .stockName(bs.stockName())
                    .quantity(bs.quantity())
                    .build()
            )   
            .toList();
        
            bankStockRepository.saveAll(bankStocks);
    }
}
