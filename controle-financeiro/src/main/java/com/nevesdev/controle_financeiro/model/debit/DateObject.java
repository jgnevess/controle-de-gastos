package com.nevesdev.controle_financeiro.model.debit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DateObject<T> {

    private T start;
    private T end;
}
