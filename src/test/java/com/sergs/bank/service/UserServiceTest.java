package com.sergs.bank.service;

import static org.mockito.Mockito.times;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sergs.bank.model.dto.NewUserDto;
import com.sergs.bank.model.dto.UserDto;
import com.sergs.bank.model.entity.UserEntity;
import com.sergs.bank.model.mapper.UserMapper;
import com.sergs.bank.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    private UserServiceImpl service;
    
    private UserEntity userEntity;

    @BeforeEach
    public void setup(){
        service = new UserServiceImpl(repository, mapper);

        userEntity = new UserEntity();

        userEntity.setUserId(1L);
        userEntity.setPassword("admin");
        userEntity.setEmail("newemail@company.com");
        userEntity.setUsername("newusername");    
    }

    @Test
    @DisplayName("Should retrieve Users by its Id")
    public void getUserByIdTest(){
        UserDto dto = new UserDto(1L, "user1", "admin", "user1@company.com");

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(userEntity));
        Mockito.when(mapper.toDto(userEntity)).thenReturn(dto);

        UserDto user1 = service.getUser(1L);

        Assertions.assertThat(user1.getUserId()).isEqualTo(1L);
        Assertions.assertThat(user1.getEmail()).isEqualTo("user1@company.com");
        Assertions.assertThat(user1.getPassword()).isEqualTo("admin");
        Assertions.assertThat(user1.getUsername()).isEqualTo("user1");
    }

    @Test
    @DisplayName("Should update a user")
    public void updateUserTest(){
        UserDto dto = new UserDto(1L, "newusername", "admin", "newemail@company.com");
        userEntity.setEmail("newemail@company.com");
        userEntity.setUsername("newusername");

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(userEntity));
        Mockito.when(mapper.updateEntityFromDto(userEntity, dto)).thenReturn(userEntity);

        service.updateUser(dto);

        Assertions.assertThat(userEntity.getUserId()).isEqualTo(1L);
        Assertions.assertThat(userEntity.getEmail()).isEqualTo("newemail@company.com");
        Assertions.assertThat(userEntity.getPassword()).isEqualTo("admin");
        Assertions.assertThat(userEntity.getUsername()).isEqualTo("newusername");
    }

    @Test
    @DisplayName("Should create a new user")
    public void createUserTest(){
        NewUserDto dto = new NewUserDto();

        dto.setEmail("newemail@company.com");
        dto.setUsername("newusername");
        dto.setPassword("admin");

        Mockito.when(mapper.toEntity(dto)).thenReturn(userEntity);
        Mockito.when(repository.save(userEntity)).thenReturn(userEntity);

        service.createUser(dto);

        Assertions.assertThat(userEntity.getEmail()).isEqualTo("newemail@company.com");
        Assertions.assertThat(userEntity.getPassword()).isEqualTo("admin");
        Assertions.assertThat(userEntity.getUsername()).isEqualTo("newusername");
    }

    @Test
    @DisplayName("Should delete a User by its Id")
    public void deleteUserByIdTest(){
        service.deleteUser(10L);
        
        Mockito.verify(repository, times(1)).deleteById(10L);

        Assertions.assertThat(repository.findById(10L).isPresent()).isFalse();
    }
}
