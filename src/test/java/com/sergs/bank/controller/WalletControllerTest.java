package com.sergs.bank.controller;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.sergs.bank.exception.EntityNotFoundException;
import com.sergs.bank.model.dto.WalletDto;
import com.sergs.bank.service.WalletService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WebMvcTest(controllers = WalletController.class)
public class WalletControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService service;

    @Test
    @DisplayName("Should retrieve a wallet by its Id")
    public void getWalletByIdTest_OK() throws Exception{
        WalletDto walletDto = new WalletDto();

        Mockito.when(service.getWallet(1L)).thenReturn(walletDto);

        mockMvc.perform(get("/wallets/1"))
            .andExpect(status().is(200))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("User not found in database")
    public void getUserByIdTest_NotFound() throws Exception{
        Mockito.when(service.getWallet(1L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/wallets/1"))
            .andExpect(status().is(404));
    }
}
