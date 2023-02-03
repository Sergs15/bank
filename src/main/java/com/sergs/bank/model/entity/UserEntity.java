package com.sergs.bank.model.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class UserEntity{
    @Id
    @SequenceGenerator(name = "user_seq", 
        sequenceName = "user_seq", 
        initialValue = 11)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Nonnull 
    @Column(unique=true) 
    private Long userId;

    @Nonnull @Column(unique=true) private String username;
    @Nonnull private String password;
    @Nonnull @Column(unique=true) private String email;
}
