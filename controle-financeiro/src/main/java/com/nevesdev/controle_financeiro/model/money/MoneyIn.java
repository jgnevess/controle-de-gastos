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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    private User user;

}
