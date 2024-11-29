package com.nevesdev.controle_financeiro.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nevesdev.controle_financeiro.model.category.Category;
import com.nevesdev.controle_financeiro.model.debit.Debit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "tb_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String username;

    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
    private String password;

    private boolean active;

    private boolean expired;

    private boolean credentials;

    private boolean locked;

    @Column(columnDefinition = "TEXT")
    private String image;

    @OneToMany
    @JsonIgnore
    private List<Debit> debits = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();

    public User(UserIn in, String encriptedPassword) {
        this.fullName = in.getFullName();
        this.username = in.getUsername();
        this.expired = false;
        this.locked = false;
        this.credentials = false;
        this.active = true;
        this.password = encriptedPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
