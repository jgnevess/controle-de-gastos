package com.nevesdev.controle_financeiro.repository;

import com.nevesdev.controle_financeiro.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByUserId(UUID userId);
}
