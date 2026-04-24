package com.github.r00j3k.simplified_stock_market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @NotBlank
    @Size(max = 255)
    @Column(name = "stock_name", length = 255, nullable = false, updatable = false)
    private String stockName;

    @NotNull
    @Column(name = "quantity", nullable = false)
    @PositiveOrZero
    @Builder.Default
    private Long quantity = 0L;
}
