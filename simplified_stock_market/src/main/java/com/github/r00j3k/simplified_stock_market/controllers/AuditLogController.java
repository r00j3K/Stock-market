package com.github.r00j3k.simplified_stock_market.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.r00j3k.simplified_stock_market.dtos.AuditLogResponseDto;
import com.github.r00j3k.simplified_stock_market.services.AuditLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class AuditLogController {
    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<AuditLogResponseDto> getAuditLogs(){
        return ResponseEntity.ok(auditLogService.getAuditLogs());
    }
}
