package com.sergs.bank.service;

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
import org.web3j.protocol.Web3j;
import org.web3j.tx.Transfer;

import com.sergs.bank.model.dto.TransactionDetailDto;
import com.sergs.bank.model.dto.TransactionResumeDto;
import com.sergs.bank.model.entity.TransactionEntity;
import com.sergs.bank.model.mapper.TransactionMapper;
import com.sergs.bank.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository repository;

    @Mock
    private TransactionMapper mapper;

    @Mock
    private Web3j web3j;

    @Mock
    private Transfer transfer;

    private TranssactionServiceImpl service;
    private TransactionEntity entity;
    private TransactionResumeDto resumeDto;
    private TransactionDetailDto detailDto;

    @BeforeEach
    public void setup(){
        service = new TranssactionServiceImpl(repository, mapper, web3j);
        entity = new TransactionEntity();

        entity.setTransactionIndex("1");
        entity.setAmount(new BigDecimal(1));
        entity.setStatus("status");
        entity.setFromAddress("from");
        entity.setToAddress("to");

        resumeDto = new TransactionResumeDto();

        resumeDto.setTransactionIndex("1");
        resumeDto.setAmount(new BigDecimal(1));
        resumeDto.setStatus("status");
        resumeDto.setFromAddress("from");
        resumeDto.setToAddress("to");

        detailDto = new TransactionDetailDto();

        detailDto.setTransactionIndex("1");
        detailDto.setAmount(new BigDecimal(1));
        detailDto.setStatus("status");
        detailDto.setFromAddress("from");
        detailDto.setToAddress("to");
    }

    @Test
    @DisplayName("Should retrieve incoming transactions for a wallet")
    public void getIncomingTransactionsForWalletTest(){
        Mockito.when(repository.findAllByToAddress(Mockito.anyString())).thenReturn(List.of(entity));
        Mockito.when(mapper.toResumeDto(entity)).thenReturn(resumeDto);

        List<TransactionResumeDto> transactions = service.getIncomingTransactionsForWallet(Mockito.anyString());

        Assertions.assertThat(transactions).containsOnly(resumeDto);

        Assertions.assertThat(transactions.get(0).getAmount()).isEqualTo(entity.getAmount());
        Assertions.assertThat(transactions.get(0).getTransactionIndex()).isEqualTo(entity.getTransactionIndex());
        Assertions.assertThat(transactions.get(0).getStatus()).isEqualTo(entity.getStatus());
        Assertions.assertThat(transactions.get(0).getFromAddress()).isEqualTo(entity.getFromAddress());
        Assertions.assertThat(transactions.get(0).getToAddress()).isEqualTo(entity.getToAddress());
    }

    @Test
    @DisplayName("Should retrieve outgoing transactions for a wallet")
    public void getOutgoingTransactionsForWalletTest(){
        Mockito.when(repository.findAllByFromAddress(Mockito.anyString())).thenReturn(List.of(entity));
        Mockito.when(mapper.toResumeDto(entity)).thenReturn(resumeDto);

        List<TransactionResumeDto> transactions = service.getOutgoingTransactionsForWallet(Mockito.anyString());

        Assertions.assertThat(transactions).containsOnly(resumeDto);

        Assertions.assertThat(transactions.get(0).getAmount()).isEqualTo(entity.getAmount());
        Assertions.assertThat(transactions.get(0).getTransactionIndex()).isEqualTo(entity.getTransactionIndex());
        Assertions.assertThat(transactions.get(0).getStatus()).isEqualTo(entity.getStatus());
        Assertions.assertThat(transactions.get(0).getFromAddress()).isEqualTo(entity.getFromAddress());
        Assertions.assertThat(transactions.get(0).getToAddress()).isEqualTo(entity.getToAddress());
    }

    @Test
    @DisplayName("Get the transaction details by its transacionIndex")
    public void getTransactionDetailsByIndexTest(){
        Mockito.when(repository.findByTransactionIndex(Mockito.anyString())).thenReturn(Optional.of(entity));
        Mockito.when(mapper.toDetailDto(entity)).thenReturn(detailDto);

        TransactionDetailDto dto = service.getTransactionDetailsByIndex(Mockito.anyString());

        Assertions.assertThat(dto.getAmount()).isEqualTo(entity.getAmount());
        Assertions.assertThat(dto.getTransactionIndex()).isEqualTo(entity.getTransactionIndex());
        Assertions.assertThat(dto.getStatus()).isEqualTo(entity.getStatus());
        Assertions.assertThat(dto.getFromAddress()).isEqualTo(entity.getFromAddress());
        Assertions.assertThat(dto.getToAddress()).isEqualTo(entity.getToAddress());
    }
}
