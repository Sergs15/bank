package com.sergs.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergs.bank.model.entity.WalletEntity;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

}
