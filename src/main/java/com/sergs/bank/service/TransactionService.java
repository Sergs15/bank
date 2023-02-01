package com.sergs.bank.service;

import java.math.BigDecimal;
import java.util.List;

import org.web3j.crypto.Credentials;

import com.sergs.bank.model.dto.TransactionDetailDto;
import com.sergs.bank.model.dto.TransactionInputDto;
import com.sergs.bank.model.dto.TransactionResumeDto;

public interface TransactionService {
    TransactionDetailDto getTransactionDetailsByIndex(String index);
    List<TransactionResumeDto> getIncomingTransactionsForWallet(String address);
    List<TransactionResumeDto> getOutgoingTransactionsForWallet(String address);
    TransactionDetailDto makeDeposit(TransactionInputDto dto);
    TransactionDetailDto makeTransactionBetweenAccounts(Credentials sender, TransactionInputDto dto);
    BigDecimal getBalanceInEth(String address);
}
