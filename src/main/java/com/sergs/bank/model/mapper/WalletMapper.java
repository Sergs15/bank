package com.sergs.bank.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.sergs.bank.model.dto.NewWalletDto;
import com.sergs.bank.model.dto.WalletDto;
import com.sergs.bank.model.entity.WalletEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WalletMapper {
    WalletDto toDto(WalletEntity entity);
    WalletEntity toEntity(NewWalletDto dto);
}
