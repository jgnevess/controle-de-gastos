package com.nevesdev.controle_financeiro.repository;

import com.nevesdev.controle_financeiro.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findByUsername(String username);
}
