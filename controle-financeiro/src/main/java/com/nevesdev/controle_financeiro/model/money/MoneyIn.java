package com.nevesdev.controle_financeiro.model.money;

import com.nevesdev.controle_financeiro.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class MoneyIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Positive
    private double value;

    @Column(name = "entry_date")
    private LocalDateTime date;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    private User user;

    public MoneyIn(MoneyInIn in, User user) {
        this.description = in.getDescription();
        this.value = in.getValue();
        this.date = in.getDate();
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
