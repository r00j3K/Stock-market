package com.github.r00j3k.simplified_stock_market.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Wallet {
    @Id
    @NotBlank
    @Size(max = 255)
    @Column(name = "wallet_id", length = 255, nullable = false, updatable = false)
    private String walletId;
}
