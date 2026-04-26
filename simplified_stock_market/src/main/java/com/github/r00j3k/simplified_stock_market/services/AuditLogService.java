package com.github.r00j3k.simplified_stock_market.services;

import org.springframework.stereotype.Service;

import com.github.r00j3k.simplified_stock_market.entities.AuditLog;
import com.github.r00j3k.simplified_stock_market.enums.TransactionType;
import com.github.r00j3k.simplified_stock_market.repositories.AuditLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;

    public void logWalletTransaction(String walletId, String stockName, TransactionType transactionType){
        auditLogRepository.save(
            AuditLog.builder()
                .walletId(walletId)
                .stockName(stockName)
                .transactionType(transactionType)
                .build()
        );
    }
}
