package com.nevesdev.controle_financeiro.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIn {
    private String fullName;
    private String username;
    private String password;
    private String confirmPassword;
    private String image;
}
