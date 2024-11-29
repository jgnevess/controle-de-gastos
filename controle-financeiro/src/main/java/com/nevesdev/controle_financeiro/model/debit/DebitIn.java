package com.nevesdev.controle_financeiro.model.debit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DebitIn {

    private String description;

    private Double value;

    private LocalDateTime date;

    private String category;

    private UUID userId;

    public DebitIn(Debit debit) {
        this.category = debit.getCategory();
        this.userId = debit.getUser().getId();
        this.date = debit.getDate();
        this.value = debit.getValue();
        this.description = debit.getDescription();
    }
}
