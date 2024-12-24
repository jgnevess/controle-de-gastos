package com.nevesdev.controle_financeiro.model.debit;

import com.nevesdev.controle_financeiro.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Debit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Positive
    private double value;

    @Column(name = "debit_date")
    private LocalDateTime date;

    @Column(nullable = false, name = "due_date")
    private LocalDateTime dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private String Category;

    @ManyToOne
    private User user;

    public Debit(DebitIn in, User user) {
        this.description = in.getDescription();
        this.Category = in.getCategory();
        this.date = in.getDate();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.value = in.getValue();
        this.user = user;
        this.dueDate = in.getDueDate();
    }

}
