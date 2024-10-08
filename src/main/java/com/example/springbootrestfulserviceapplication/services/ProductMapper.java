package com.example.springbootrestfulserviceapplication.services;

import com.example.springbootrestfulserviceapplication.models.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("link"),
                rs.getString("owner"),
                rs.getString("contacts"),
                rs.getInt("category_id"),
                rs.getString("status")
        );
    }
}
