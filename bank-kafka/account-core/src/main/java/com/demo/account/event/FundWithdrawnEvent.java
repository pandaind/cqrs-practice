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
public class FundWithdrawnEvent extends BaseEvent {
    private double amount;
}
