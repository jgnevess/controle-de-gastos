package com.nevesdev.controle_financeiro.service;

import com.nevesdev.controle_financeiro.model.debit.ListByMonth;
import com.nevesdev.controle_financeiro.model.money.MoneyIn;
import com.nevesdev.controle_financeiro.model.money.MoneyInIn;
import com.nevesdev.controle_financeiro.model.money.MoneyInOut;
import com.nevesdev.controle_financeiro.model.money.MoneyInUpdate;
import com.nevesdev.controle_financeiro.model.user.User;
import com.nevesdev.controle_financeiro.repository.MoneyInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class MoneyInService {

    @Autowired
    private MoneyInRepository moneyInRepository;

    @Autowired
    private UserService userService;

    public MoneyInOut register(MoneyInIn in) {
        User u = userService.getUserById(in.getUserId());
        MoneyIn moneyIn = new MoneyIn(in, u);
        moneyIn = moneyInRepository.save(moneyIn);
        return new MoneyInOut(moneyIn);
    }

    public ListByMonth findAllByUser(UUID userId) {
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        int day = 1;
        LocalDate startDate = LocalDate.of(currentYear, currentMonth, day);
        LocalDateTime start = startDate.atStartOfDay();
        if(currentMonth == 12) {
            currentMonth = 1;
            currentYear++;
            day = 31;
            startDate = LocalDate.of(currentYear, currentMonth, day);
        }
        LocalDate endDate = startDate.withDayOfMonth(LocalDate.now().lengthOfMonth());
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        List<MoneyIn> min = moneyInRepository.findAllByUserIdAndMonth(userId, start, end);
        List<MoneyInOut> out = min.stream().map(MoneyInOut::new).toList();
        double total = 0.0;
        for(MoneyIn o : min) {
            total += o.getValue();
        }
        return new ListByMonth(out, total);
    }

    public MoneyInOut findById(Long id) {
        MoneyInOut moneyInOut = new MoneyInOut(moneyInRepository.findById(id).orElseThrow());
        return moneyInOut;
    }

    public MoneyInOut update(Long id, UUID userId, MoneyInUpdate update) {
        User user = userService.getUserById(userId);
        MoneyIn min = moneyInRepository.findById(id).orElseThrow();
        if(!min.getUser().equals(user)) {
            return null;
        }
        min.setDescription(update.description());
        min.setValue(update.value());
        min = moneyInRepository.save(min);
        return new MoneyInOut(min);
    }

    public String delete(Long id, UUID userId) {
        User user = userService.getUserById(userId);
        MoneyIn min = moneyInRepository.findById(id).orElseThrow();
        if(!min.getUser().equals(user)) {
            return "Você não tem permissão!";
        }
        moneyInRepository.delete(min);
        return "Excluido permanentemente";
    }
}
