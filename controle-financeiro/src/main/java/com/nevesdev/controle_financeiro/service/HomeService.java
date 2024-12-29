package com.nevesdev.controle_financeiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class HomeService {

    @Autowired
    private MoneyInService moneyInService;

    @Autowired
    private DebitService debitService;

    public Map<String, Double> findDifference(UUID userId, LocalDate startDate, LocalDate endDate) {
        Double debitValue = debitService.findAllByUserIdByPeriod(userId, startDate, endDate, "date-up").getTotal();
        Double inValue = moneyInService.findAllByUser(userId).getTotal();
        Map<String, Double> res = new HashMap<>();
        res.put("Entrada", inValue);
        res.put("Gasto", debitValue);
        res.put("Total", inValue - debitValue);
        return res;
    }

    public Map<String, Double> findDifferenceParam(UUID userId, LocalDate startDate, LocalDate endDate) {
        Double debitValue = debitService.findAllByUserIdByPeriod(userId, startDate, endDate, "date-up").getTotal();
        Double inValue = moneyInService.findAllByUser(userId, startDate, endDate).getTotal();
        Map<String, Double> res = new HashMap<>();
        res.put("Entrada", inValue);
        res.put("Gasto", debitValue);
        res.put("Total", inValue - debitValue);
        return res;
    }

}
