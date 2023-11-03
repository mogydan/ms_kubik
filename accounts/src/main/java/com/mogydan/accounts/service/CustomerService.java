package com.mogydan.accounts.service;

import com.mogydan.accounts.dto.CustomerDetailsDto;

public interface CustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
