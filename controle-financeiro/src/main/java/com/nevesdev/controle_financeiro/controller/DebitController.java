package com.nevesdev.controle_financeiro.controller;

import com.nevesdev.controle_financeiro.model.debit.*;
import com.nevesdev.controle_financeiro.service.DebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping(value = "debit")
public class DebitController {

    @Autowired
    private DebitService debitService;

    @PostMapping(value = "/create")
    public ResponseEntity<DebitOut> create(@RequestBody @Validated DebitIn debitIn) {
        return ResponseEntity.ok(debitService.registerDebit(debitIn));
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<ListByMonth> getAll(@PathVariable UUID userId, @RequestParam String sortBy) {
        return ResponseEntity.ok(debitService.findAllByUserId(userId, sortBy));
    }

    @GetMapping(value = "id/{id}")
    public ResponseEntity<DebitOut> findById(@PathVariable Long id) {
        return ResponseEntity.ok(debitService.findById(id));
    }

    @PostMapping(value = "period/{userId}")
    public ResponseEntity<ListByMonth> getAllByPeriod(
            @PathVariable UUID userId,
            @RequestBody DebitDate date,
            @RequestParam String sortBy) {
        return ResponseEntity.ok(debitService.findAllByUserIdByPeriod(userId, date.start(), date.end(), sortBy));
    }

    @PutMapping(value = "update")
    public ResponseEntity<?> updateDebit(
            @RequestParam("id") Long id,
            @RequestParam("userId") UUID userId,
            @RequestBody DebitUpdate debitUpdate
    ) {
        DebitOut response = debitService.updateDebit(id, userId, debitUpdate);
        if(response == null) return ResponseEntity.status(500).body("Ocorreu um erro na atualização do debito");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<?> deleteDebit(
            @RequestParam("id") Long id,
            @RequestParam("userId") UUID userId
    ) {
        String response = debitService.deleteDebit(id, userId);
        if(response.startsWith("Você não tem permissão")) return ResponseEntity.status(500).body(response);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "date")
    public ResponseEntity<DateObject<LocalDate>> getDate() {
        return ResponseEntity.ok(debitService.startEnd());
    }
}
