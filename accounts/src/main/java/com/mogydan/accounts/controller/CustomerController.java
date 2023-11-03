package com.mogydan.accounts.controller;

import com.mogydan.accounts.dto.CustomerDetailsDto;
import com.mogydan.accounts.dto.CustomerDto;
import com.mogydan.accounts.dto.ErrorResponseDto;
import com.mogydan.accounts.service.CustomerService;
import com.mogydan.accounts.service.client.CardsFeignClient;
import com.mogydan.accounts.service.client.LoansFeignClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.mogydan.accounts.constants.AccountsConstants.CORRELATION_ID;

@Tag(name = "REST API for Customers in Bank", description = "REST APIs in Bank FETCH to  account details")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Fetch Customer Details REST API", description = "REST API to fetch Customer details based on a mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader(CORRELATION_ID) String correlationId,
                                                                   @RequestParam
                                                                   @Pattern(regexp = "(^$|[0-9]{10})", message = "AccountNumber must be 10 digits")
                                                                   String mobileNumber) {
        log.debug("fetchCustomerDetails method start");
        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber, correlationId);
        log.debug("fetchCustomerDetails method end");
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerDetailsDto);
    }
}
