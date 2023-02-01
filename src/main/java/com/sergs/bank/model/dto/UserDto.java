package com.sergs.bank.model.dto;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    @Nonnull private Long userId;
    @Nonnull private String username;
    @Nonnull private String password;
    @Nonnull private String email;
}
