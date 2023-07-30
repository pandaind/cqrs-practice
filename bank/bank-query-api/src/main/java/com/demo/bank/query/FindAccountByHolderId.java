package com.demo.bank.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByHolderId {
    private String accountHolderId;
}
