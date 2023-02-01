package com.sergs.bank.controller;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergs.bank.model.dto.DepositDto;
import com.sergs.bank.model.dto.NewWalletDto;
import com.sergs.bank.model.dto.TransactionDetailDto;
import com.sergs.bank.model.dto.TransactionInputDto;
import com.sergs.bank.model.dto.WalletDto;
import com.sergs.bank.service.WalletService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/wallets")
@AllArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{walletId}")
    @Transactional(readOnly = true)
    public WalletDto getWallet(@PathVariable Long walletId){
        return walletService.getWallet(walletId);
    }

    @PostMapping("/create")
    @Transactional
    public void createWallet(@RequestBody NewWalletDto dto){
        walletService.createWallet(dto);
    }

    @DeleteMapping("/{walletId}")
    @Transactional
    public void deleteWallet(@PathVariable Long walletId){
        walletService.deleteWallet(walletId);
    }

    @GetMapping("/{walletId}/balance")
    @Transactional(readOnly = true)
    public BigDecimal getWalletBalanceInEth(@PathVariable Long walletId){
        return walletService.getWalletBalanceInEth(walletId);
    }

    @PutMapping("/{walletId}/transaction")
    @Transactional
    public TransactionDetailDto makeTransactionBetweenAccounts(@PathVariable Long walletId, @RequestBody TransactionInputDto dto){
        return walletService.makeTransactionBetweenAccounts(walletId, dto);
    }

    @PutMapping("/{walletId}/deposit")
    @Transactional
    public TransactionDetailDto makeDeposit(@PathVariable Long walletId, @RequestBody DepositDto dto){
        return walletService.makeDeposit(walletId, dto);
    }
}
