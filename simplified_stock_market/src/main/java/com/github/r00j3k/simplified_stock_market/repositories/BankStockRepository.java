package com.github.r00j3k.simplified_stock_market.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.r00j3k.simplified_stock_market.entities.BankStock;

import jakarta.persistence.LockModeType;

public interface BankStockRepository extends JpaRepository<BankStock, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM BankStock b WHERE b.stockName = :stockName")
    Optional<BankStock> findByIdForUpdate(@Param("stockName") String stockName);
}
