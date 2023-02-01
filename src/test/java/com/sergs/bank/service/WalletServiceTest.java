package com.sergs.bank.service;

import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sergs.bank.model.dto.NewWalletDto;
import com.sergs.bank.model.dto.TransactionResumeDto;
import com.sergs.bank.model.dto.WalletDto;
import com.sergs.bank.model.entity.WalletEntity;
import com.sergs.bank.model.mapper.WalletMapper;
import com.sergs.bank.repository.WalletRepository;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {
    @Mock
    private WalletRepository repository;

    @Mock
    private WalletMapper mapper;

    @Mock
    private TransactionService transactionService;

    private WalletServiceImpl service;
    private WalletEntity entity;
    private WalletDto dto;
    private List<TransactionResumeDto> transactionsInc;
    private List<TransactionResumeDto> transactionsOut;

 
    @BeforeEach
    public void setup(){
        service = new WalletServiceImpl(repository, mapper, transactionService);
        String tmpdir = System.getProperty("java.io.tmpdir");
        service.setWalletDirectory(tmpdir);

        entity = new WalletEntity();

        entity.setAddress("address");
        entity.setPassword("admin");
        entity.setUserId(1L);
        entity.setWalletId(1L);
        entity.setWalletName("walletName");

        dto = new WalletDto();

        dto.setAddress("address");
        dto.setBalance(new BigDecimal(0));
        dto.setWalletName("walletName");
        dto.setWalletId(1L);
        dto.setUserId(1L);

        TransactionResumeDto txn = new TransactionResumeDto();

        txn.setAmount(new BigDecimal(1));
        txn.setTransactionIndex("1");
        transactionsInc = List.of(txn);

        dto.setIncomingTransactions(transactionsInc);

        TransactionResumeDto txn2 = new TransactionResumeDto();

        txn.setAmount(new BigDecimal(2));
        txn.setTransactionIndex("2");
        transactionsOut= List.of(txn2);

        dto.setOutgoingTransactions(transactionsOut);
    }

    @Test
    @DisplayName("Retrieves a Wallet by its Id")
    public void getWalletTest(){
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(entity));
        Mockito.when(mapper.toDto(entity)).thenReturn(dto);
        Mockito.when(transactionService.getBalanceInEth(entity.getAddress())).thenReturn(new BigDecimal(0));
        Mockito.when(transactionService.getIncomingTransactionsForWallet(entity.getAddress())).thenReturn(transactionsInc);
        Mockito.when(transactionService.getOutgoingTransactionsForWallet(entity.getAddress())).thenReturn(transactionsOut);
        
        WalletDto dto = service.getWallet(1L);

        Assertions.assertThat(dto.getWalletId()).isEqualTo(entity.getWalletId());
        Assertions.assertThat(dto.getBalance()).isEqualTo(new BigDecimal(0));
        Assertions.assertThat(dto.getAddress()).isEqualTo(entity.getAddress());
        Assertions.assertThat(dto.getWalletName()).isEqualTo(entity.getWalletName());
        Assertions.assertThat(dto.getUserId()).isEqualTo(entity.getUserId());
        Assertions.assertThat(dto.getIncomingTransactions()).isEqualTo(transactionsInc);
        Assertions.assertThat(dto.getOutgoingTransactions()).isEqualTo(transactionsOut);
    }

    @Test
    @DisplayName("Create a new Wallet")
    public void createWalletTest(){
        NewWalletDto newWallet = new NewWalletDto(1L, "admin");
        Mockito.when(mapper.toEntity(newWallet)).thenReturn(entity);
        Mockito.when(repository.save(entity)).thenReturn(entity);

        service.createWallet(newWallet);
        
        Assertions.assertThat(newWallet.getUserId()).isEqualTo(entity.getUserId());
        Assertions.assertThat(newWallet.getPassword()).isEqualTo(entity.getPassword());
    }

    @Test
    @DisplayName("Delete a Wallet by its Id")
    public void deleteWalletTest(){
        service.deleteWallet(1L);
        Mockito.verify(repository, times(1)).deleteById(1L);

        Assertions.assertThat(repository.findById(1L).isPresent()).isFalse();
    }
}
