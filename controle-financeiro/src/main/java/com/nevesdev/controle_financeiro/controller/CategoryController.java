package com.nevesdev.controle_financeiro.controller;

import com.nevesdev.controle_financeiro.model.category.Category;
import com.nevesdev.controle_financeiro.model.category.CategoryIn;
import com.nevesdev.controle_financeiro.model.category.CategoryOut;
import com.nevesdev.controle_financeiro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryOut> saveCategory(@RequestBody CategoryIn categoryIn) {
        return ResponseEntity.ok(categoryService.saveCategory(categoryIn));
    }

    @GetMapping
    public ResponseEntity<List<CategoryOut>> getAllCategories(@RequestParam UUID userId) {
        return ResponseEntity.ok(categoryService.findAllByUserId(userId));
    }


    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<String> delete(@PathVariable UUID userId, @RequestParam Long id) {
        String res = categoryService.deleteCategory(userId, id);
        if(res.startsWith("Não")) return ResponseEntity.badRequest().body(res);
        return ResponseEntity.ok(res);
    }

}
