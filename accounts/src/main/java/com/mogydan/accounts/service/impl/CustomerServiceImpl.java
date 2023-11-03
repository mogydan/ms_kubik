package com.mogydan.accounts.service.impl;

import com.mogydan.accounts.dto.*;
import com.mogydan.accounts.entity.Accounts;
import com.mogydan.accounts.entity.Customer;
import com.mogydan.accounts.exception.ResourceNotFoundException;
import com.mogydan.accounts.mapper.AccountsMapper;
import com.mogydan.accounts.mapper.CustomerMapper;
import com.mogydan.accounts.repository.AccountsRepository;
import com.mogydan.accounts.repository.CustomerRepository;
import com.mogydan.accounts.service.CustomerService;
import com.mogydan.accounts.service.client.CardsFeignClient;
import com.mogydan.accounts.service.client.LoansFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final LoansFeignClient loansFeignClient;
    private final CardsFeignClient cardsFeignClient;


    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString()));
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(account, new AccountsDto());

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if (loansDtoResponseEntity != null) {
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        if (cardsDtoResponseEntity != null) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }

        customerDetailsDto.setAccountsDto(accountsDto);

        return customerDetailsDto;
    }
}
