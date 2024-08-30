package com.example.springbootrestfulserviceapplication.services;

import com.example.springbootrestfulserviceapplication.exceptions.NoProductsFoundException;
import com.example.springbootrestfulserviceapplication.models.Category;
import com.example.springbootrestfulserviceapplication.repository.CategoryRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategory(){
        return categoryRepository.getAllCategory()
                .orElseThrow(() -> new NoProductsFoundException("No category found in the database."));
    }
}
