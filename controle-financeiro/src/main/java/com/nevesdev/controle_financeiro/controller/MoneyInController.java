package com.nevesdev.controle_financeiro.controller;

import com.nevesdev.controle_financeiro.model.debit.ListByMonth;
import com.nevesdev.controle_financeiro.model.money.MoneyInIn;
import com.nevesdev.controle_financeiro.model.money.MoneyInOut;
import com.nevesdev.controle_financeiro.model.money.MoneyInUpdate;
import com.nevesdev.controle_financeiro.service.MoneyInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "money")
public class MoneyInController {

    @Autowired
    private MoneyInService service;

    @PostMapping
    public ResponseEntity<MoneyInOut> register(@RequestBody MoneyInIn in) {
        return ResponseEntity.ok(service.register(in));
    }

    @GetMapping
    public ResponseEntity<ListByMonth> findAll(@RequestParam UUID userId) {
        return ResponseEntity.ok(service.findAllByUser(userId));
    }

    @GetMapping(value = "find/{id}")
    public ResponseEntity<MoneyInOut> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<MoneyInOut> update(@PathVariable Long id, @RequestParam UUID userId, @RequestBody MoneyInUpdate up) {
        MoneyInOut res = service.update(id, userId, up);
        if(res == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(res);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestParam UUID userId) {
        String res = service.delete(id, userId);
        if(res == null) return ResponseEntity.badRequest().body(res);
        return ResponseEntity.ok(res);
    }
}
