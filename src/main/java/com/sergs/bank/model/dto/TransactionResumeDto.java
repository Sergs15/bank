package com.sergs.bank.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.web3j.utils.Convert.Unit;

import lombok.Data;

@Data
public class TransactionResumeDto {
    private String transactionHash;
    private String transactionIndex;
    private String gasUsed;
    private String status;
    private String fromAddress;
    private String toAddress;
    private BigDecimal amount;
    private Unit unit;
    private Date date;
}
