package com.sergs.bank.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sergs.bank.model.dto.NewUserDto;
import com.sergs.bank.model.dto.UserDto;
import com.sergs.bank.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @Transactional(readOnly = true)
    @ResponseBody
    public UserDto getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }

    @PostMapping("/create")
    @Transactional
    public void createUser(@RequestBody NewUserDto dto){
        userService.createUser(dto);
    }

    @PutMapping("/{userId}/update")
    @Transactional
    public void updateUser(@PathVariable Long userId, @RequestBody UserDto dto){
        dto.setUserId(userId);
        userService.updateUser(dto);
    }

    @DeleteMapping("/{userId}")
    @Transactional
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }
}
