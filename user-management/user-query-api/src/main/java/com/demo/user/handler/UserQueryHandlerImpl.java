package com.demo.user.handler;

import com.demo.user.dto.UserLookupResponse;
import com.demo.user.query.FindAllUserQuery;
import com.demo.user.query.FindUserByIdQuery;
import com.demo.user.query.SearchUserQuery;
import com.demo.user.repository.UserRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQueryHandlerImpl implements UserQueryHandler {

    private final UserRepository repository;

    @Autowired
    public UserQueryHandlerImpl(UserRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    @Override
    public UserLookupResponse getUserById(FindUserByIdQuery query) {
        var user = repository.findById(query.getId());
        return user.map(UserLookupResponse::new).orElse(null);
    }

    @QueryHandler
    @Override
    public UserLookupResponse searchUser(SearchUserQuery query) {
        var users = repository.findByFilterRegex(query.getFilter());
        return new UserLookupResponse(users);
    }

    @QueryHandler
    @Override
    public UserLookupResponse getAllUser(FindAllUserQuery query) {
        var users = repository.findAll();
        return new UserLookupResponse(users);
    }
}
