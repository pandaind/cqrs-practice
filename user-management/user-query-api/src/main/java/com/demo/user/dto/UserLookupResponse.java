package com.demo.user.dto;

import com.demo.user.core.dto.BaseResponse;
import com.demo.user.core.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserLookupResponse extends BaseResponse {
    private List<User> users;

    public UserLookupResponse(String message) {
        super(message);
    }

    public UserLookupResponse(List<User> users) {
        super(null);
        this.users = users;
    }

    public UserLookupResponse(User user) {
        super(null);
        this.users = new ArrayList<>();
        this.users.add(user);
    }
}
