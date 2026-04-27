package com.github.r00j3k.simplified_stock_market.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.r00j3k.simplified_stock_market.entities.WalletStock;
import com.github.r00j3k.simplified_stock_market.entities.WalletStockId;

import jakarta.persistence.LockModeType;

public interface WalletStockRepository extends JpaRepository<WalletStock, WalletStockId> {
    List<WalletStock> findAllByWalletStockIdWalletId(String walletId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ws FROM WalletStock ws WHERE ws.walletStockId = :id")
    Optional<WalletStock> findByIdForUpdate(@Param("id") WalletStockId id);
}
