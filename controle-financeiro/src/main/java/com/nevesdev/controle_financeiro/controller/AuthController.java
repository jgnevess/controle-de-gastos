package com.nevesdev.controle_financeiro.controller;

import com.nevesdev.controle_financeiro.model.user.User;
import com.nevesdev.controle_financeiro.model.user.UserIn;
import com.nevesdev.controle_financeiro.model.user.UserLoginIn;
import com.nevesdev.controle_financeiro.model.user.UserOut;
import com.nevesdev.controle_financeiro.service.AuthService;
import com.nevesdev.controle_financeiro.service.TokenService;
import com.nevesdev.controle_financeiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserOut> register(@RequestBody @Validated UserIn userIn) {
        UserOut response = service.createUser(userIn);
        if(response != null) return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map> login(@RequestBody UserLoginIn userLoginIn) {
        UsernamePasswordAuthenticationToken userLogin = new UsernamePasswordAuthenticationToken(userLoginIn.username(), userLoginIn.password());
        Authentication auth = this.manager.authenticate(userLogin);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        Map<String, String> response = new HashMap<>();
        User user = (User) auth.getPrincipal();
        response.put("token", token);
        response.put("userId", user.getId().toString());
        response.put("img", user.getImage());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/image")
    public ResponseEntity<String> removeImg(@RequestParam("userId") UUID userId) {
        userService.removePicture(userId);
        return ResponseEntity.ok("Deletado");
    }

    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") UUID userId
    ) {
        String response = userService.setImage(file, userId);
        if(response.startsWith("Nenhum arquivo foi enviado")) return ResponseEntity.notFound().build();
        if(response.startsWith("Imagem enviada com sucesso")) return ResponseEntity.ok("Imagem enviada com sucesso");
        return ResponseEntity.status(500).body("Erro ao salvar imagem");
    }

    @GetMapping(value = "/valid")
    public ResponseEntity<?> tokenIsValid(@RequestParam String token) {
        Boolean res = !tokenService.validateToken(token).equals("");
        if(!res) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(res);
    }


}

