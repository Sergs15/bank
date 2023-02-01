package com.sergs.bank.model.dto;

import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
public class NewUserDto {
    @Nonnull private String username;
    @Nonnull private String password;
    @Nonnull private String email;
}
