package com.nevesdev.controle_financeiro.model.debit;

import java.time.LocalDateTime;

public record DebitUpdate(String description, double value, String category, LocalDateTime dueDate) {
}
