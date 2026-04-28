package com.github.r00j3k.simplified_stock_market.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class WalletStockId implements Serializable {
    @NotNull
    @Size(max = 255)
    @Column(name = "wallet_id", length = 255, nullable = false)
    private String walletId;

    @NotNull
    @Column(name = "stock_id", nullable = false)
    private Long stockId;
}
