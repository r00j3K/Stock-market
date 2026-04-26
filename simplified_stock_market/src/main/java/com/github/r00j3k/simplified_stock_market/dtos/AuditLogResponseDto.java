package com.github.r00j3k.simplified_stock_market.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuditLogResponseDto(
    @JsonProperty("log")
    List<AuditLogDto> logs
) {}
