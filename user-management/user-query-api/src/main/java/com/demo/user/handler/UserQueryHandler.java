package com.demo.user.handler;

import com.demo.user.dto.UserLookupResponse;
import com.demo.user.query.FindAllUserQuery;
import com.demo.user.query.FindUserByIdQuery;
import com.demo.user.query.SearchUserQuery;

public interface UserQueryHandler {
    UserLookupResponse getUserById(FindUserByIdQuery query);

    UserLookupResponse searchUser(SearchUserQuery query);

    UserLookupResponse getAllUser(FindAllUserQuery query);
}
