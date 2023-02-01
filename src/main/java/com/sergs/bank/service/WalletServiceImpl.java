package com.sergs.bank.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import com.sergs.bank.exception.EntityNotFoundException;
import com.sergs.bank.exception.InternalServerException;
import com.sergs.bank.model.dto.DepositDto;
import com.sergs.bank.model.dto.NewWalletDto;
import com.sergs.bank.model.dto.TransactionDetailDto;
import com.sergs.bank.model.dto.TransactionInputDto;
import com.sergs.bank.model.dto.WalletDto;
import com.sergs.bank.model.entity.WalletEntity;
import com.sergs.bank.model.mapper.WalletMapper;
import com.sergs.bank.repository.WalletRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class WalletServiceImpl implements WalletService{

    private final WalletRepository repository;

    private final WalletMapper mapper;

    private final TransactionService transactionService;

    @Value("${wallet.directory.path}")
    private String walletDirectory;

    public WalletServiceImpl(WalletRepository repository, WalletMapper mapper, TransactionService transactionService) {
        this.repository = repository;
        this.mapper = mapper;
        this.transactionService = transactionService;
    }

    @Override
    public WalletDto getWallet(Long walletId) {
        WalletDto dto = mapper.toDto(findById(walletId));

        dto.setBalance(getWalletBalanceInEth(walletId));
        dto.setIncomingTransactions(transactionService.getIncomingTransactionsForWallet(dto.getAddress()));
        dto.setOutgoingTransactions(transactionService.getOutgoingTransactionsForWallet(dto.getAddress()));
        
        return dto;
    }

    @Override
    public void createWallet(NewWalletDto dto) {
        WalletEntity entity = mapper.toEntity(dto);

        String walletName = generateNewWalletFile(dto);
        entity.setWalletName(walletName);

        String walletAddress = loadWalletCredentials(walletName, dto.getPassword()).getAddress();
        entity.setAddress(walletAddress);

        repository.save(entity);
    }

    @Override
    public void deleteWallet(Long walletId) {
        repository.deleteById(walletId);
    }

    @Override
    public TransactionDetailDto makeTransactionBetweenAccounts(Long walletId, TransactionInputDto dto){
        WalletEntity wallet = findById(walletId);
        Credentials sender = loadWalletCredentials(wallet.getWalletName(), wallet.getPassword());

        return transactionService.makeTransactionBetweenAccounts(sender, dto);
    }

    @Override
    public TransactionDetailDto makeDeposit(Long walletId, DepositDto dto){
        TransactionInputDto txnInput = new TransactionInputDto();

        txnInput.setAmount(dto.getAmount());
        txnInput.setUnit(dto.getUnit());
        txnInput.setToAddress(findById(walletId).getAddress());

        return transactionService.makeDeposit(txnInput);
    }

    @Override
    public BigDecimal getWalletBalanceInEth(Long walletId){
        String address = findById(walletId).getAddress();
        return transactionService.getBalanceInEth(address);
    }

    private Credentials loadWalletCredentials(String walletName, String password) {
        try {
            return WalletUtils.loadCredentials(password, walletDirectory + "/" + walletName);
        } catch (IOException | CipherException e) {
            log.error("Error while loading wallet credentials for: " + walletName, e);
            throw new InternalServerException("Error loading wallet credentials", e);
        }
    }

    private String generateNewWalletFile(NewWalletDto dto) {
        try {
            return WalletUtils.generateNewWalletFile(dto.getPassword(), new File(walletDirectory));
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
                | CipherException | IOException e) {
            log.error("Error in wallet creation" , e);
            throw new InternalServerException("Error in wallet creation" , e);
        }
    }

    private WalletEntity findById(Long walletId){
        return repository.findById(walletId)
            .orElseThrow(() -> new EntityNotFoundException("Wallet not found with walletId: " + walletId));
    }

    public void setWalletDirectory(String walletDirectory){
        this.walletDirectory = walletDirectory;
    }

}
