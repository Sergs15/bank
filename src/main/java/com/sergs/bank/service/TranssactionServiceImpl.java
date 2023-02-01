package com.sergs.bank.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.sergs.bank.exception.EntityNotFoundException;
import com.sergs.bank.exception.InternalServerException;
import com.sergs.bank.model.dto.TransactionDetailDto;
import com.sergs.bank.model.dto.TransactionInputDto;
import com.sergs.bank.model.dto.TransactionResumeDto;
import com.sergs.bank.model.entity.TransactionEntity;
import com.sergs.bank.model.mapper.TransactionMapper;
import com.sergs.bank.repository.TransactionRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TranssactionServiceImpl implements TransactionService{

    private final TransactionRepository repository;

    private final TransactionMapper mapper;

    private final Web3j web3j;

    @Value("${ganache.account.privatekey}")
    private String bankAccountPK;

    public TranssactionServiceImpl(TransactionRepository repository, TransactionMapper mapper, Web3j web3j) {
        this.repository = repository;
        this.mapper = mapper;
        this.web3j = web3j;
    }

    @Override
    public TransactionDetailDto getTransactionDetailsByIndex(String index) {
        return repository.findByTransactionIndex(index).map(mapper::toDetailDto).orElseThrow(() -> new EntityNotFoundException("Transaction not found with index: " + index));
    }

    @Override
    public List<TransactionResumeDto> getIncomingTransactionsForWallet(String address) {
        return repository.findAllByToAddress(address).stream().map(mapper::toResumeDto).collect(Collectors.toList());
    }

    @Override
    public List<TransactionResumeDto> getOutgoingTransactionsForWallet(String address) {
        return repository.findAllByFromAddress(address).stream().map(mapper::toResumeDto).collect(Collectors.toList());
    }

    @Override
    public TransactionDetailDto makeDeposit(TransactionInputDto dto) {
        Credentials bankAccount = Credentials.create(bankAccountPK);

        return makeTransactionBetweenAccounts(bankAccount, dto);
    }

    @Override
    public TransactionDetailDto makeTransactionBetweenAccounts(Credentials sender, TransactionInputDto dto){
        try {
            TransactionReceipt txn = Transfer.sendFunds(web3j, sender, dto.getToAddress(), dto.getAmount(), dto.getUnit()).send();
            
            TransactionDetailDto txnDto = mapper.fromReceiptToDetailDto(txn);
            txnDto.setAmount(dto.getAmount());
            txnDto.setUnit(dto.getUnit());
            txnDto.setDate(new Date());
            
            recordTransactionInDB(txnDto);

            return txnDto;
        } catch (Exception e) {
            log.error("Error while performing transaction from " + sender.getAddress() + " to " + dto.getToAddress(), e);
            throw new InternalServerException("Error while performing transacion", e);
        }
    }

    @Override
    public BigDecimal getBalanceInEth(String address) {
        try {
            EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            return Convert.fromWei(balance.getBalance().toString(), Unit.ETHER);
        } catch (IOException e) {
            log.error("Error while fetching balance from " + address, e);
            throw new InternalServerException("Errror while fetching balance", e);
        }
    }
    
    private TransactionEntity recordTransactionInDB(TransactionDetailDto dto) {
        return repository.save(mapper.toEntity(dto));
    }
}
