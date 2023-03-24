package com.demo.user.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchUserQuery {
    private String filter;
}
