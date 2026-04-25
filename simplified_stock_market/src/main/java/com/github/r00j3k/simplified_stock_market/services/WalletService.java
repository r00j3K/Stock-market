package com.github.r00j3k.simplified_stock_market.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.r00j3k.simplified_stock_market.entities.AuditLog;
import com.github.r00j3k.simplified_stock_market.entities.BankStock;
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
            case BUY:
                buyStock(walletId, stockName);
                break;
            case SELL:
                sellStock(walletId, stockName);
                break;
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
        var bankStock = bankStockRepository.findById(stockName)
                .orElseThrow(() -> new StockNotFoundException("The stock of provided name was not found"));
            
        if(bankStock.getQuantity() == 0){
            throw new StockNotAvailableException("This stock is currently unavailable.");
        }
            
        bankStock.setQuantity(bankStock.getQuantity()-1);
        bankStockRepository.save(bankStock);

        if(walletRepository.findById(walletId).isEmpty()){
            walletRepository.save(
                Wallet.builder()
                .walletId(walletId)
                .build()
            );
        }
            
        var walletStockOptional = walletStockRepository.findById(new WalletStockId((walletId), stockName));
        if(walletStockOptional.isEmpty()){
            walletStockRepository.save(
                WalletStock.builder()
                    .walletStockId(new WalletStockId(walletId, stockName))
                    .quantity(1L)
                    .build()
            );
        }
        else {
            var walletStock = walletStockOptional.get();
            walletStock.setQuantity(walletStock.getQuantity()+1);
            walletStockRepository.save(walletStock);
        }
    }

    private void sellStock(String walletId, String stockName){
        // if the wallet of provided id does not exist, then the there is no point of selling operation
        walletRepository.findById(walletId)
            .orElseThrow(() -> new WalletNotFoundException("Wallet of provided id was not found"));
        
        var walletStock = walletStockRepository.findById(new WalletStockId(walletId, stockName))
            .orElseThrow(() -> new StockNotAvailableException("This wallet does not possess this stock"));
        
        walletStock.setQuantity(walletStock.getQuantity()-1);
        if(walletStock.getQuantity() == 0){
            walletStockRepository.delete(walletStock);
        }
        else{
            walletStockRepository.save(walletStock);
        }

        var bankStock = bankStockRepository.findById(stockName)
            .orElseThrow(() -> new StockNotFoundException("The stock of provided name was not found"));
        
        bankStock.setQuantity(bankStock.getQuantity()+1);
        bankStockRepository.save(bankStock);
    }
}
