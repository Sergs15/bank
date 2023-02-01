package com.sergs.bank.model.dto;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewWalletDto {
    @Nonnull private Long userId;
    @Nonnull private String password;
}
