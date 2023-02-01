package com.sergs.bank.model.dto;

import java.math.BigDecimal;

import org.web3j.utils.Convert.Unit;

import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
public class DepositDto {
    @Nonnull private BigDecimal amount;
    @Nonnull private Unit unit;
}
