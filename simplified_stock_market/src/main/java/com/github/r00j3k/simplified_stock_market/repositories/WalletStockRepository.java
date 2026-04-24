package com.github.r00j3k.simplified_stock_market.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.r00j3k.simplified_stock_market.entities.WalletStock;
import com.github.r00j3k.simplified_stock_market.entities.WalletStockId;

public interface WalletStockRepository extends JpaRepository<WalletStock,WalletStockId> {
    List<WalletStock> findAllByWalletWalletId(String walletId);
}
