package com.github.r00j3k.simplified_stock_market.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.r00j3k.simplified_stock_market.entities.AuditLog;
import com.github.r00j3k.simplified_stock_market.entities.Wallet;
import com.github.r00j3k.simplified_stock_market.entities.WalletStock;
import com.github.r00j3k.simplified_stock_market.entities.WalletStockId;
import com.github.r00j3k.simplified_stock_market.enums.TransactionType;
import com.github.r00j3k.simplified_stock_market.exceptions.StockNotAvailableException;
import com.github.r00j3k.simplified_stock_market.exceptions.StockNotFoundException;
import com.github.r00j3k.simplified_stock_market.exceptions.WalletNotFoundException;
import com.github.r00j3k.simplified_stock_market.repositories.AuditLogRepository;
import com.github.r00j3k.simplified_stock_market.repositories.BankStockRepository;
import com.github.r00j3k.simplified_stock_market.repositories.WalletRepository;
import com.github.r00j3k.simplified_stock_market.repositories.WalletStockRepository;
import com.github.r00j3k.simplified_stock_market.dtos.StockList;
import com.github.r00j3k.simplified_stock_market.dtos.StockQuantityResponse;
import com.github.r00j3k.simplified_stock_market.dtos.WalletStocksResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletStockRepository walletStockRepository;
    private final BankStockRepository bankStockRepository;
    private final WalletRepository walletRepository;
    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void trade(String walletId, String stockName, String type){
        var transactionType = TransactionType.valueOf(type.toUpperCase());

        switch(transactionType){
            case BUY -> buyStock(walletId, stockName);
            case SELL -> sellStock(walletId, stockName);
        }

        auditLogRepository.save(
            AuditLog.builder()
                .walletId(walletId)
                .stockName(stockName)
                .transactionType(transactionType)
                .build()
        );
    }

    private void buyStock(String walletId, String stockName){
        var bankStock = bankStockRepository.findByIdForUpdate(stockName)
                .orElseThrow(() -> new StockNotFoundException("Stock not found."));
            
        if(bankStock.getQuantity() == 0){
            throw new StockNotAvailableException("This stock is currently unavailable.");
        }
            
        bankStock.setQuantity(bankStock.getQuantity()-1);

        if(!walletRepository.existsById(walletId)){
            walletRepository.save(
                Wallet.builder()
                .walletId(walletId)
                .build()
            );
        }
            
        walletStockRepository.findByIdForUpdate(new WalletStockId((walletId), stockName))
            .ifPresentOrElse(
                ws -> ws.setQuantity(ws.getQuantity()+1),
                () -> walletStockRepository.save(
                    WalletStock.builder()
                    .walletStockId(new WalletStockId(walletId, stockName))
                    .quantity(1L)
                    .build()
                )
            );
    }

    private void sellStock(String walletId, String stockName){
        // fetching bankStock and walletStock in the same order as in the butStock method to prevent deadlock
        var bankStock = bankStockRepository.findByIdForUpdate(stockName)
            .orElseThrow(() -> new StockNotFoundException("Stock not found."));

        // if the wallet of provided id does not exist, then there is no point of selling operation
        walletRepository.findById(walletId)
            .orElseThrow(() -> new WalletNotFoundException("Wallet not found."));
        
        var walletStock = walletStockRepository.findByIdForUpdate(new WalletStockId(walletId, stockName))
            .orElseThrow(() -> new StockNotAvailableException("This wallet does not possess this stock."));

        walletStock.setQuantity(walletStock.getQuantity()-1);

        if(walletStock.getQuantity() == 0){
            walletStockRepository.delete(walletStock);
        }
        
        bankStock.setQuantity(bankStock.getQuantity()+1);
    }

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
