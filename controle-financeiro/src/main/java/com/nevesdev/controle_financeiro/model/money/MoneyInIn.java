package com.nevesdev.controle_financeiro.model.money;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoneyInIn {
    private String description;
    private double value;
    private LocalDateTime date;
    private UUID userId;

    public MoneyInIn(MoneyIn money) {
        description = money.getDescription();
        value = money.getValue();
        date = money.getDate();
        userId = money.getUser().getId();
    }
}
