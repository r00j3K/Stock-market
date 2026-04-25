package com.github.r00j3k.simplified_stock_market.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wallet_stocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class WalletStock {
    @EmbeddedId
    private WalletStockId walletStockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("walletId")
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stockName")
    @JoinColumn(name = "stock_name", nullable = false)
    private BankStock bankStock;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Long quantity;
}
