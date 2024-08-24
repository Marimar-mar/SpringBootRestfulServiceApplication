package com.example.springbootrestfulserviceapplication;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllProducts() {
        List<Category> category = categoryService.getAllCategory();
        return ResponseEntity.ok(category); // Возвращаем ResponseEntity с статусом 200 OK и телом ответа
    }
}
