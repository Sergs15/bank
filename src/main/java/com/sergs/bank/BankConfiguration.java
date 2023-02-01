package com.sergs.bank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import okhttp3.OkHttpClient;

@Configuration
public class BankConfiguration {
    @Value("${web3j.client-address}")
    private String clientAddress;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(clientAddress, new OkHttpClient.Builder().build()));
    }
}
