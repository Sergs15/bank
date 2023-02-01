package com.sergs.bank.service;

import java.math.BigDecimal;

import com.sergs.bank.model.dto.DepositDto;
import com.sergs.bank.model.dto.NewWalletDto;
import com.sergs.bank.model.dto.TransactionDetailDto;
import com.sergs.bank.model.dto.TransactionInputDto;
import com.sergs.bank.model.dto.WalletDto;

public interface WalletService {
    void createWallet(NewWalletDto dto);
    void deleteWallet(Long walletId);
    WalletDto getWallet(Long walletId);
    TransactionDetailDto makeTransactionBetweenAccounts(Long walletId, TransactionInputDto dto);
    TransactionDetailDto makeDeposit(Long walletId, DepositDto dto);
    BigDecimal getWalletBalanceInEth(Long walletId);
}
