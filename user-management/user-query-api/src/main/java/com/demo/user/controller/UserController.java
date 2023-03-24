package com.demo.user.controller;

import com.demo.user.dto.UserLookupResponse;
import com.demo.user.query.FindAllUserQuery;
import com.demo.user.query.FindUserByIdQuery;
import com.demo.user.query.SearchUserQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final QueryGateway queryGateway;

    public UserController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/")
    public ResponseEntity<UserLookupResponse> getAllUsers() {
        try {
            var query = new FindAllUserQuery();
            return getResponseEntity(queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)));
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get all user request";
            log.error("Error {}", safeErrorMessage);
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserLookupResponse> getUserById(@PathVariable("id") String id) {
        try {
            var query = new FindUserByIdQuery(id);
            return getResponseEntity(queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)));
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get user by id request";
            log.error("Error {}", safeErrorMessage);
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/search/{filter}")
    public ResponseEntity<UserLookupResponse> searchUserByFilter(@PathVariable("filter") String filter) {
        try {
            var query = new SearchUserQuery(filter);
            return getResponseEntity(queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)));
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete search user request";
            log.error("Error {}", safeErrorMessage);
            e.printStackTrace();
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<UserLookupResponse> getResponseEntity(CompletableFuture<UserLookupResponse> queryGateway) {
        var response = queryGateway.join();
        if (response == null || response.getUsers() == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
