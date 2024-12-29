package com.nevesdev.controle_financeiro.model.money;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HomeIn {

    private LocalDate start;
    private LocalDate end;

}
