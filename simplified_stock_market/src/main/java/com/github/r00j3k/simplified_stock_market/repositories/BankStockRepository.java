package com.github.r00j3k.simplified_stock_market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.r00j3k.simplified_stock_market.entities.BankStock;

public interface BankStockRepository extends JpaRepository<BankStock, String> {
    
}
