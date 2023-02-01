package com.sergs.bank.model.dto;

import java.math.BigDecimal;

import org.web3j.utils.Convert.Unit;

import lombok.Data;

@Data
public class TransactionInputDto {
    private String toAddress;
    private BigDecimal amount;
    private Unit unit;
}
