package com.nevesdev.controle_financeiro.controller;


import com.nevesdev.controle_financeiro.model.money.HomeIn;
import com.nevesdev.controle_financeiro.service.DebitService;
import com.nevesdev.controle_financeiro.service.HomeService;
import com.nevesdev.controle_financeiro.service.MoneyInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @PostMapping
    public ResponseEntity<Map<String, Double>> findDiff(@RequestParam UUID userId, @RequestBody HomeIn in) {
        return ResponseEntity.ok(homeService.findDifferenceParam(userId, in.getStart(), in.getEnd()));
    }
}
