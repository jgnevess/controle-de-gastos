package com.nevesdev.controle_financeiro.model.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryOut {
    private Long id;
    private String category;

    public CategoryOut(Category category) {
        this.id = category.getId();
        this.category = category.getCategory();
    }
}
