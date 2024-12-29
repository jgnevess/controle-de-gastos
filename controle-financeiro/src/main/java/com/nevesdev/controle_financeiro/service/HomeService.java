package com.nevesdev.controle_financeiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class HomeService {

    @Autowired
    private MoneyInService moneyInService;

    @Autowired
    private DebitService debitService;

    public Double findDifference(UUID userId, LocalDate startDate, LocalDate endDate) {
        Double debitValue = debitService.findAllByUserIdByPeriod(userId, startDate, endDate, "date-up").getTotal();
        Double inValue = moneyInService.findAllByUser(userId).getTotal();
        System.out.println(debitValue);
        System.out.println(inValue);
        return inValue - debitValue;
    }

}
