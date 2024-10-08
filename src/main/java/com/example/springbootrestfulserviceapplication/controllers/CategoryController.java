package com.example.springbootrestfulserviceapplication.controllers;


import com.example.springbootrestfulserviceapplication.services.CategoryService;
import com.example.springbootrestfulserviceapplication.models.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin//для доступа к фронту
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //@CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> category = categoryService.getAllCategory();
        return ResponseEntity.ok(category); // Возвращаем ResponseEntity с статусом 200 OK и телом ответа
    }
}
