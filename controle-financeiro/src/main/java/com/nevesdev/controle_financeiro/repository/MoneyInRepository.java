package com.nevesdev.controle_financeiro.repository;

import com.nevesdev.controle_financeiro.model.debit.Debit;
import com.nevesdev.controle_financeiro.model.money.MoneyIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MoneyInRepository extends JpaRepository<MoneyIn, Long> {

    @Query(value = "SELECT * FROM money_in WHERE user_id = :userId and debit_date between :start and :end", nativeQuery = true)
    List<Debit> findAllByUserIdAndMonth(UUID userId, LocalDateTime start, LocalDateTime end);

}
