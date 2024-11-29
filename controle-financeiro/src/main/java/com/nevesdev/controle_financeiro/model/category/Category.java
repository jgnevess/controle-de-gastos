package com.nevesdev.controle_financeiro.model.category;

import com.nevesdev.controle_financeiro.model.user.User;
import com.nevesdev.controle_financeiro.service.UserService;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "tb_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    @ManyToOne
    private User user;

    public Category(CategoryIn categoryIn, User user) {
        this.category = categoryIn.getCategory();
        this.user = user;
    }

}
