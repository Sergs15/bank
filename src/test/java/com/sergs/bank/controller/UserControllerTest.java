package com.sergs.bank.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.sergs.bank.exception.EntityNotFoundException;
import com.sergs.bank.model.dto.UserDto;
import com.sergs.bank.service.UserService;

@WebMvcTest(controllers = UserController.class)
@WithMockUser(username = "user1")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    @DisplayName("Should retrieve Users by its Id")
    public void getUserByIdTest_OK() throws Exception{
        UserDto user = new UserDto(1L, "username", "admin", "user1@company.com");

        Mockito.when(service.getUser(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
            .andExpect(status().is(200))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("User not found in database")
    public void getUserByIdTest_NotFound() throws Exception{
        Mockito.when(service.getUser(1L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/users/1"))
            .andExpect(status().is(404));
    }
    
}
