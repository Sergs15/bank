package com.sergs.bank.service;

import com.sergs.bank.model.dto.NewUserDto;
import com.sergs.bank.model.dto.UserDto;

public interface UserService {
    void createUser(NewUserDto dto);
    void updateUser(UserDto dto);
    void deleteUser(Long userId);
    UserDto getUser(Long userId);
}
