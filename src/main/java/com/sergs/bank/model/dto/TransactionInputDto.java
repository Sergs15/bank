package com.sergs.bank.model.dto;

import java.math.BigDecimal;

import org.web3j.utils.Convert.Unit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TransactionInputDto {
    private String toAddress;
    private BigDecimal amount;
    @Schema(description = "Units allowed: WEI, KWEI, MWEI, SZABO, FINNEY, ETHER, KETHER, METHER, GETHER")
    private Unit unit;
}
