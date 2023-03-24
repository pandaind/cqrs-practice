package com.demo.user.controller;

import com.demo.user.command.RegisterUserCommand;
import com.demo.user.command.RemoveUserCommand;
import com.demo.user.command.UpdateUserCommand;
import com.demo.user.core.dto.BaseResponse;
import com.demo.user.dto.RegisterUserResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    private final CommandGateway commandGateway;

    @Autowired
    public UserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new RegisterUserResponse(id, "User successfully registered"), HttpStatus.CREATED);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing register user request for id - " + command.getId();
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(new RegisterUserResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable("id") String id,
                                                   @Valid @RequestBody UpdateUserCommand command) {
        try {
            command.setId(id);
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new BaseResponse("User successfully updated"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing update user request for id - " + id;
            log.error("Error {}", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<BaseResponse> removeUser(@PathVariable("id") String id) {
        try {
            commandGateway.send(new RemoveUserCommand(id));
            return new ResponseEntity<>(new BaseResponse("User successfully removed"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing remove user request for id - " + id;
            log.error("Error {}", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
