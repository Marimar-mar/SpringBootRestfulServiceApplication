package com.example.springbootrestfulserviceapplication.repository;

import com.example.springbootrestfulserviceapplication.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Optional<List<Category>> getAllCategory();
}
