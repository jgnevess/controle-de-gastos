package com.nevesdev.controle_financeiro.repository;

import com.nevesdev.controle_financeiro.model.debit.Debit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface DebitRepository extends JpaRepository<Debit, Long> {

    @Query(value = "SELECT * FROM debit WHERE user_id = :userId and due_date between :start and :end", nativeQuery = true)
    List<Debit> findAllByUserIdAndMonth(UUID userId, LocalDateTime start, LocalDateTime end);
}
