package com.nevesdev.controle_financeiro.model.debit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ListByMonth {

    private List<DebitOut> debitOuts;
    private double total;

    public ListByMonth(List<DebitOut> debit, double total) {
        this.debitOuts = debit;
        this.total = total;
    }
}
