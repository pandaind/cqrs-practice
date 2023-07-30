package com.demo.bank.dto;

import com.demo.bank.core.dto.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenAccountResponse extends BaseResponse {

    private String id;

    public OpenAccountResponse(String id, String message) {
        super(message);
        this.id = id;
    }
}
