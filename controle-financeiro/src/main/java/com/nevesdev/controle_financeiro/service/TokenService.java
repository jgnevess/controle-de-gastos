package com.nevesdev.controle_financeiro.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nevesdev.controle_financeiro.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm alg = Algorithm.HMAC256(secret);
            String token = JWT.create().withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpDate())
                    .sign(alg);
            return token;
        } catch(JWTCreationException ex) {
            throw new RuntimeException("ERROR! Error while generation token", ex);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm alg = Algorithm.HMAC256(secret);
            return JWT.require(alg)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch(JWTVerificationException ex) {
            return "";
        }
    }

    private Instant generateExpDate() {
        Instant i = LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
        System.out.println(i.toString());
        return i;
    }

}
