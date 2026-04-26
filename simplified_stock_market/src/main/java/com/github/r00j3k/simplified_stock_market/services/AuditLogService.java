package com.github.r00j3k.simplified_stock_market.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.r00j3k.simplified_stock_market.dtos.AuditLogDto;
import com.github.r00j3k.simplified_stock_market.dtos.AuditLogResponseDto;
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

    public AuditLogResponseDto getAuditLogs(){
        List<AuditLogDto> auditLogs = auditLogRepository.findTop10000ByOrderByLogIdAsc()
            .stream()
            .map(
                al -> new AuditLogDto(al.getTransactionType().name().toLowerCase(), al.getWalletId(), al.getStockName())
            )
            .toList();
        
        return new AuditLogResponseDto(auditLogs);
    }
}
