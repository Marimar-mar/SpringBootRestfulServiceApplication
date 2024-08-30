package com.example.springbootrestfulserviceapplication.services;

import com.example.springbootrestfulserviceapplication.models.Category;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Category(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
