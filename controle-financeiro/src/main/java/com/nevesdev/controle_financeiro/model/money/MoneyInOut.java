package com.nevesdev.controle_financeiro.model.money;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoneyInOut extends MoneyInIn{

    private Long id;

    public MoneyInOut(MoneyIn money) {
        super(money);
        this.id = money.getId();
    }
}
