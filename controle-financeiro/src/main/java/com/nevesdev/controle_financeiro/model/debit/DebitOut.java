package com.nevesdev.controle_financeiro.model.debit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DebitOut extends DebitIn {
    private Long id;

    public DebitOut(Debit debit) {
        super(debit);
        this.id = debit.getId();
    }
}
