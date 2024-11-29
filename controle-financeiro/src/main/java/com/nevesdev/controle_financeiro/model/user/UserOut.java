package com.nevesdev.controle_financeiro.model.user;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class UserOut {

    private String fullName;
    private String username;

    public UserOut(User user) {
        this.fullName = user.getFullName();
        this.username = user.getUsername();
    }
}
