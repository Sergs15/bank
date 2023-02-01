package com.sergs.bank.model.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class WalletDto {
    private Long walletId;
    private String walletName;
    private String address;
    private BigDecimal balance;
    private Long userId;

    private List<TransactionResumeDto> incomingTransactions;
    private List<TransactionResumeDto> outgoingTransactions;
}
