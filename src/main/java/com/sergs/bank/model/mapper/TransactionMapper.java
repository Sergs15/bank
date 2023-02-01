package com.sergs.bank.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.sergs.bank.model.dto.TransactionDetailDto;
import com.sergs.bank.model.dto.TransactionResumeDto;
import com.sergs.bank.model.entity.TransactionEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {
    TransactionResumeDto toResumeDto(TransactionEntity entity);
    TransactionDetailDto toDetailDto(TransactionEntity entity);

    @Mapping(target = "fromAddress", source = "from")
    @Mapping(target = "toAddress", source = "to")
    TransactionDetailDto fromReceiptToDetailDto(TransactionReceipt entity);

    TransactionEntity toEntity(TransactionDetailDto receipt);
}
