package com.nevesdev.controle_financeiro.model.debit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ListByMonth<T> {

    private List<T> outs;
    private double total;

    public ListByMonth(List<T> out, double total) {
        this.outs = out;
        this.total = total;
    }
}
