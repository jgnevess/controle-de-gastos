package com.nevesdev.controle_financeiro.service;

import com.nevesdev.controle_financeiro.model.debit.*;
import com.nevesdev.controle_financeiro.model.user.User;
import com.nevesdev.controle_financeiro.repository.DebitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DebitService {

    @Autowired
    private DebitRepository debitRepository;

    @Autowired
    private UserService userService;

    public DebitOut registerDebit(DebitIn debitIn) {
        Debit debit = new Debit(debitIn, userService.getUserById(debitIn.getUserId()));
        debit = debitRepository.save(debit);
        return new DebitOut(debit);
    }

    public ListByMonth findAllByUserId(UUID userId, String sortBy) {
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        LocalDate startDate = LocalDate.of(currentYear, currentMonth, 1);
        LocalDateTime start = startDate.atStartOfDay();
        LocalDate endDate = startDate.withDayOfMonth(LocalDate.now().lengthOfMonth());
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        List<Debit> debits = debitRepository.findAllByUserIdAndMonth(userId, start, end);
        List<DebitOut> debitOuts = new ArrayList<>();
        switch (sortBy) {
            case "date-up":
                debitOuts = debits.stream()
                        .map(DebitOut::new)
                        .sorted((d1, d2) -> d1.getDate().compareTo(d2.getDate()))
                        .collect(Collectors.toList());
                break;
            case "date-down":
                debitOuts = debits.stream()
                        .map(DebitOut::new)
                        .sorted((d2, d1) -> d1.getDate().compareTo(d2.getDate()))
                        .collect(Collectors.toList());
                break;
            case "description-up":
                debitOuts = debits.stream()
                        .map(DebitOut::new)
                        .sorted((d1, d2) -> d1.getDescription().compareTo(d2.getDescription()))
                        .collect(Collectors.toList());
                break;
            case "description-down":
                debitOuts = debits.stream()
                        .map(DebitOut::new)
                        .sorted((d2, d1) -> d1.getDescription().compareTo(d2.getDescription()))
                        .collect(Collectors.toList());
                break;
            case "value-up":
                debitOuts = debits.stream()
                        .map(DebitOut::new)
                        .sorted((d1, d2) -> d1.getValue().compareTo(d2.getValue()))
                        .collect(Collectors.toList());
                break;
            case "value-down":
                debitOuts = debits.stream()
                        .map(DebitOut::new)
                        .sorted((d2, d1) -> d1.getValue().compareTo(d2.getValue()))
                        .collect(Collectors.toList());
                break;
        }
        double total = 0;
        for (Debit d: debits) {
            total += d.getValue();
        }
        return new ListByMonth(debitOuts, total);
    }

    public DebitOut findById(Long id) {
        Debit debit = debitRepository.findById(id).orElseThrow();
        return new DebitOut(debit);
    }

    public ListByMonth findAllByUserIdByPeriod(
            UUID userId,
            LocalDate startDate,
            LocalDate endDate,
            String sortBy) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        List<Debit> debits = debitRepository.findAllByUserIdAndMonth(userId, start, end);
        List<DebitOut> debitOuts = new ArrayList<>();
        switch (sortBy) {
            case "date-up":
                debitOuts = debits.stream()
                        .map(DebitOut::new)
                        .sorted((d1, d2) -> d1.getDate().compareTo(d2.getDate()))
                        .collect(Collectors.toList());
                break;
            case "date-down":
                debitOuts = debits.stream()
                        .map(DebitOut::new)
                        .sorted((d2, d1) -> d1.getDate().compareTo(d2.getDate()))
                        .collect(Collectors.toList());
                break;
            case "description-up":
                debitOuts = debits.stream()
                        .map(DebitOut::new)
                        .sorted((d1, d2) -> d1.getDescription().compareTo(d2.getDescription()))
                        .collect(Collectors.toList());
                break;
            case "description-down":
                debitOuts = debits.stream()
                        .map(DebitOut::new)
                        .sorted((d2, d1) -> d1.getDescription().compareTo(d2.getDescription()))
                        .collect(Collectors.toList());
                break;
            case "value-up":
                debitOuts = debits.stream()
                        .map(DebitOut::new)
                        .sorted((d1, d2) -> d1.getValue().compareTo(d2.getValue()))
                        .collect(Collectors.toList());
                break;
            case "value-down":
                debitOuts = debits.stream()
                        .map(DebitOut::new)
                        .sorted((d2, d1) -> d1.getValue().compareTo(d2.getValue()))
                        .collect(Collectors.toList());
                break;
        }
        double total = 0;
        for (Debit d: debits) {
            total += d.getValue();
        }
        return new ListByMonth(debitOuts, total);
    }

    public DebitOut updateDebit(Long id, UUID userId, DebitUpdate debitUpdate) {
        User user = userService.getUserById(userId);
        Debit debit = debitRepository.findById(id).orElseThrow();
        if(!debit.getUser().equals(user)) {
            return null;
        }
        debit.setCategory(debitUpdate.category());
        debit.setDescription(debitUpdate.description());
        debit.setValue(debitUpdate.value());
        debitRepository.save(debit);
        return new DebitOut(debit);
    }

    public String deleteDebit(Long id, UUID userId) {
        User user = userService.getUserById(userId);
        Debit debit = debitRepository.findById(id).orElseThrow();
        if(!debit.getUser().equals(user)) {
            return "Você não tem permissão para apagar esse debito!";
        }
        debitRepository.delete(debit);
        return "Debito excluido permanentemente";
    }

    public DateObject<LocalDate> startEnd() {
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        LocalDate startDate = LocalDate.of(currentYear, currentMonth, 1);
        LocalDate endDate = LocalDate.of(currentYear, currentMonth, LocalDate.now().getDayOfMonth());
        DateObject<LocalDate> res = new DateObject<>();
        res.setStart(startDate);
        res.setEnd(endDate);
        return res;
    }
}
