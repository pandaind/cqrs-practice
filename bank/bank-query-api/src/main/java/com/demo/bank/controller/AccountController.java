package com.demo.bank.controller;

import com.demo.bank.dto.AccountLookupResponse;
import com.demo.bank.dto.EqualityType;
import com.demo.bank.query.FindAccountByHolderId;
import com.demo.bank.query.FindAccountByIdQuery;
import com.demo.bank.query.FindAccountWithBalanceQuery;
import com.demo.bank.query.FindAllAccountQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/v1/bank")
public class AccountController {
    private final QueryGateway queryGateway;

    @Autowired
    public AccountController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            var query = new FindAllAccountQuery();
            return getResponseEntity(queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)));
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get all account request";
            log.error("Error {}", safeErrorMessage);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable("id") String id) {
        try {
            var query = new FindAccountByIdQuery(id);
            return getResponseEntity(queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)));
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get account by id request";
            log.error("Error {}", safeErrorMessage);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/holder/{holderId}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolderId(@PathVariable("holderId") String holderId) {
        try {
            var query = new FindAccountByHolderId(holderId);
            return getResponseEntity(queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)));
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get account by holder id request";
            log.error("Error {}", safeErrorMessage);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/balance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountByBalance(@PathVariable("equalityType") EqualityType equalityType,
                                                                     @PathVariable("balance") Double balance) {
        try {
            var query = new FindAccountWithBalanceQuery(equalityType, balance);
            return getResponseEntity(queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)));
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get account by balance request";
            log.error("Error {}", safeErrorMessage);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<AccountLookupResponse> getResponseEntity(CompletableFuture<AccountLookupResponse> queryGateway) {
        var response = queryGateway.join();
        if (response == null || response.getAccounts() == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
