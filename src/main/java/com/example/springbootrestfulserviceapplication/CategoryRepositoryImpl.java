package com.example.springbootrestfulserviceapplication;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository{

    private static final String SQL_GET_ALL_CATEGORY =
            "select * from categories";

    private final CategoryMapper categoryMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CategoryRepositoryImpl(CategoryMapper categoryMapper, NamedParameterJdbcTemplate jdbcTemplate) {
        this.categoryMapper = categoryMapper;
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Optional<List<Category>> getAllCategory() {
        var params = new MapSqlParameterSource();
        List<Category> category = jdbcTemplate.query(SQL_GET_ALL_CATEGORY, params, categoryMapper);
        return category.isEmpty() ? Optional.empty() : Optional.of(category);
    }
}
