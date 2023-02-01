package com.sergs.bank.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.web3j.utils.Convert.Unit;

import lombok.Data;

@Data
public class TransactionDetailDto {
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
