package com.sergs.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergs.bank.model.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByTransactionIndex(String index);
    List<TransactionEntity> findAllByToAddress(String address);
    List<TransactionEntity> findAllByFromAddress(String address);
}
