package com.demo.bank.controller;

import com.demo.bank.command.CloseAccountCommand;
import com.demo.bank.command.DepositFundsCommand;
import com.demo.bank.command.OpenAccountCommand;
import com.demo.bank.command.WithdrawFundsCommand;
import com.demo.bank.core.dto.BaseResponse;
import com.demo.bank.dto.OpenAccountResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/bank")
public class AccountController {
    private final CommandGateway commandGateway;

    @Autowired
    public AccountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping(path = "/open")
    public ResponseEntity<OpenAccountResponse> openAccount(@Valid @RequestBody OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);

        try {
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new OpenAccountResponse(id, "Opening Account id successful"), HttpStatus.CREATED);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing opening account request for id - " + command.getId();
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(new OpenAccountResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/deposit/{id}")
    public ResponseEntity<BaseResponse> depositFund(@PathVariable("id") String id,
                                                    @Valid @RequestBody DepositFundsCommand command) {
        try {
            command.setId(id);
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new BaseResponse("Amount Deposited Successfully"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing deposit account request for id - " + id;
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/withdraw/{id}")
    public ResponseEntity<BaseResponse> depositFund(@PathVariable("id") String id,
                                                    @Valid @RequestBody WithdrawFundsCommand command) {
        try {
            command.setId(id);
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new BaseResponse("Amount Withdraw Successfully"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing withdraw account request for id - " + id;
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/close/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable("id") String id) {
        try {
            commandGateway.send(CloseAccountCommand.builder().id(id).build());
            return new ResponseEntity<>(new BaseResponse("Account successfully closed."), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing close account request for id - " + id;
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
