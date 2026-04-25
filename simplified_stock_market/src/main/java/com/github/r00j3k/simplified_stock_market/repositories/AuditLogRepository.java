package com.github.r00j3k.simplified_stock_market.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.r00j3k.simplified_stock_market.entities.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findTop10000ByOrderByLogIdAsc();
}
