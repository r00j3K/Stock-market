package com.github.r00j3k.simplified_stock_market.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.r00j3k.simplified_stock_market.entities.Wallet;
import com.github.r00j3k.simplified_stock_market.entities.WalletStock;
import com.github.r00j3k.simplified_stock_market.entities.WalletStockId;
import com.github.r00j3k.simplified_stock_market.enums.TransactionType;
import com.github.r00j3k.simplified_stock_market.exceptions.StockNotAvailableException;
import com.github.r00j3k.simplified_stock_market.exceptions.StockNotFoundException;
import com.github.r00j3k.simplified_stock_market.exceptions.WalletNotFoundException;
import com.github.r00j3k.simplified_stock_market.repositories.BankStockRepository;
import com.github.r00j3k.simplified_stock_market.repositories.WalletRepository;
import com.github.r00j3k.simplified_stock_market.repositories.WalletStockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final WalletStockRepository walletStockRepository;
    private final BankStockRepository bankStockRepository;
    private final WalletRepository walletRepository;
    private final AuditLogService auditLogService;

    @Transactional
    public void trade(String walletId, String stockName, TransactionType transactionType){
        switch(transactionType){
            case BUY -> buyStock(walletId, stockName);
            case SELL -> sellStock(walletId, stockName);
        }

        auditLogService.logWalletTransaction(walletId, stockName, transactionType);
    }

    private void buyStock(String walletId, String stockName){
        var bankStock = bankStockRepository.findByStockNameForUpdate(stockName)
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
        var bankStock = bankStockRepository.findByStockNameForUpdate(stockName)
            .orElseThrow(() -> new StockNotFoundException("Stock not found."));

        // if the wallet of provided id does not exist, then there is no point of selling operation
        if (!walletRepository.existsById(walletId)) {
            throw new WalletNotFoundException("Wallet not found.");
        }
        
        var walletStock = walletStockRepository.findByIdForUpdate(new WalletStockId(walletId, stockName))
            .orElseThrow(() -> new StockNotAvailableException("This wallet does not possess this stock."));

        if(walletStock.getQuantity() <= 0){
            throw new StockNotAvailableException("This wallet does not possess this stock.");
        }

        walletStock.setQuantity(walletStock.getQuantity()-1);

        if(walletStock.getQuantity() == 0){
            walletStockRepository.delete(walletStock);
        }
        
        bankStock.setQuantity(bankStock.getQuantity()+1);
    }
}
