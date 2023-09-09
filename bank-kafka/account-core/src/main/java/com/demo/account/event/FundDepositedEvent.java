package com.demo.account.event;

import com.demo.cqrs.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FundDepositedEvent extends BaseEvent {
    private double amount;
}
