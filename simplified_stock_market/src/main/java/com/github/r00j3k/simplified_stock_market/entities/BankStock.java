package com.github.r00j3k.simplified_stock_market.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "bank_stocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BankStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id", nullable = false, updatable = false)
    private Long stockId;

    @NotBlank
    @Size(max = 255)
    @Column(name = "stock_name", length = 255, nullable = false, unique = true)
    private String stockName;

    @NotNull
    @Column(name = "quantity", nullable = false)
    @PositiveOrZero
    @Builder.Default
    private Long quantity = 0L;
}
