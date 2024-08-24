package com.example.springbootrestfulserviceapplication;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Optional<List<Category>> getAllCategory();
}
