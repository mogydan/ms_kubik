package com.mogydan.accounts.functions;

import com.mogydan.accounts.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class AccountsFunctions {

    @Bean
    public Consumer<Long> updateCommunication(AccountService accountService) {
        return accountNumber -> {
            log.info("Updating Communication status for the account number: {}", accountNumber);
            accountService.updateCommunicationStatus(accountNumber);
        };
    }
}
