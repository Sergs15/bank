package com.sergs.bank.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.web3j.utils.Convert.Unit;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "transaction")
@Data
public class TransactionEntity {
    @Id
    private String transactionHash;
    private String transactionIndex;
    private String blockHash;
    private String blockNumber;
    private String cumulativeGasUsed;
    private String gasUsed;
    private String contractAddress;
    private String root;
    private String status;
    private String fromAddress;
    private String toAddress;
    private String revertReason;
    private String type;
    private String effectiveGasPrice;
    private BigDecimal amount;
    private Unit unit;
    private Date date;
}
