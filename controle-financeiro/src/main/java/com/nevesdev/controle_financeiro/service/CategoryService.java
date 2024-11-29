package com.nevesdev.controle_financeiro.service;

import com.nevesdev.controle_financeiro.model.category.Category;
import com.nevesdev.controle_financeiro.model.category.CategoryIn;
import com.nevesdev.controle_financeiro.model.category.CategoryOut;
import com.nevesdev.controle_financeiro.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;


    public CategoryOut saveCategory(CategoryIn categoryIn) {
        Category category = new Category(categoryIn, userService.getUserById(categoryIn.getUserId()));
        return new CategoryOut(categoryRepository.save(category));
    }
    
    public List<CategoryOut> findAllByUserId(UUID userId) {
        List<Category> categories = categoryRepository.findAllByUserId(userId);
        List<CategoryOut> cout = new ArrayList<>();
        cout = categories.stream()
                .map(CategoryOut::new)
                .sorted((c1, c2)-> c1.getCategory().compareTo(c2.getCategory()))
                .toList();
        return cout;
    }

    public String deleteCategory(UUID userId, Long id) {
        Category c = categoryRepository.findById(id).orElseThrow();
        if(!Objects.equals(c.getUser(), userService.getUserById(userId))) {
            return "Não autorizado";
        }
        categoryRepository.delete(c);
        return "Categoria excluida com sucesso";
    }
}
