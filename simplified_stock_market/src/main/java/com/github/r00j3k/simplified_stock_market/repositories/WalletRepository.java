package com.github.r00j3k.simplified_stock_market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.r00j3k.simplified_stock_market.entities.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, String> {
    
}
