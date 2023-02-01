package com.sergs.bank.service;

import org.springframework.stereotype.Service;

import com.sergs.bank.exception.ConstraintViolationException;
import com.sergs.bank.exception.EntityNotFoundException;
import com.sergs.bank.model.dto.NewUserDto;
import com.sergs.bank.model.dto.UserDto;
import com.sergs.bank.model.entity.UserEntity;
import com.sergs.bank.model.mapper.UserMapper;
import com.sergs.bank.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Override
    public void createUser(NewUserDto dto) {
        checkIfUsernameAlreadyExists(dto.getUsername());
        checkIfEmailAlreadyExists(dto.getEmail());
        repository.save(mapper.toEntity(dto));
    }

    @Override
    public void updateUser(UserDto dto) {
        UserEntity user = repository.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));
        
        if(!user.getUsername().equals(dto.getUsername())){
            checkIfUsernameAlreadyExists(dto.getUsername());
        }

        if(!user.getEmail().equals(dto.getEmail())){
            checkIfEmailAlreadyExists(dto.getEmail());
        }
        
        repository.save(mapper.updateEntityFromDto(user, dto));
    }

    @Override
    public void deleteUser(Long userId) {
        repository.deleteById(userId);
    }

    @Override
    public UserDto getUser(Long userId) {
        return repository.findById(userId).map(mapper::toDto).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    private void checkIfEmailAlreadyExists(String email) {
        if(repository.existsByEmail(email)){
            log.error("Email already in use during user creation: " + email);
            throw new ConstraintViolationException("This email is already in use: " + email);
        }
    }

    private void checkIfUsernameAlreadyExists(String username){
        if(repository.existsByUsername(username)){
            log.error("Username already in use during user creation: " + username);
            throw new ConstraintViolationException("This username is already in use: " + username);
        }
    }
    
}
