package com.demo.bank.core.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundsDepositedEvent {
    private String id;
    private Double amount;
    private Double balance;
}
