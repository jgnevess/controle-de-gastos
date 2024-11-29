package com.nevesdev.controle_financeiro.service;

import com.nevesdev.controle_financeiro.model.user.User;
import com.nevesdev.controle_financeiro.model.user.UserIn;
import com.nevesdev.controle_financeiro.model.user.UserOut;
import com.nevesdev.controle_financeiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserOut createUser(UserIn userIn) {
        if(!userIn.getPassword().equals(userIn.getConfirmPassword())) {
            return null;
        }
        String encriptedPassword = new BCryptPasswordEncoder().encode(userIn.getPassword());
        User u = new User(userIn, encriptedPassword);
        u = userRepository.save(u);
        UserOut response = new UserOut(u);
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }


}
