package com.example.springbootrestfulserviceapplication;


import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {//внутренняя бизнеслогика

    private static final String SQL_GET_PRODUCT_BY_ID =
            "select id, name, description, link, owner, contacts, category_id, status from products where id = :id";

    private static final String SQL_DELETE_PRODUCT_BY_ID =
            "delete from products where id = :id";

    private static final String SQL_POST_PRODUCT =
            "INSERT INTO products (name, description, link, owner, contacts, category_id, status) " +
                    "VALUES (:name, :description, :link, :owner, :contacts, :category_id, :status)";

    // SQL-запрос для получения продуктов с определенным статусом
    private static final String SQL_GET_PRODUCTS_BY_STATUS =
            "select * from products where status = :status";

    // SQL-запрос для получения всех продуктов, вне зависимости от их статуса
    private static final String SQL_GET_ALL_PRODUCTS =
            "select * from products";

    private static final String SQL_GET_PRODUCT_BY_PRODUCT_NAME =
            "SELECT id, name,description,link,owner,contacts, category_id, status FROM products WHERE name LIKE '%'|| :ProductName ||'%'";

    private static final String SQL_GET_PRODUCTS_BY_CATEGORY_NAME =
            "SELECT p.id, p.name, p.description, p.link, p.owner, p.contacts, p.category_id, p.status " +
                    "FROM products p " +
                    "INNER JOIN categories c ON p.category_id = c.id " +
                    "WHERE c.name = :categoryName";


    private final ProductMapper productMapper;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductRepositoryImpl(
            ProductMapper productMapper, NamedParameterJdbcTemplate jdbcTemplate
    ) {
        this.productMapper = productMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Product> getProductById(int id) {
        var params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.query(SQL_GET_PRODUCT_BY_ID, params, productMapper).stream().findFirst();
    }

    @Override
    public Optional<List<Product>> getProductsByStatus(String status) {
        var params = new MapSqlParameterSource();
        params.addValue("status", status);
        List<Product> products = jdbcTemplate.query(SQL_GET_PRODUCTS_BY_STATUS, params, productMapper);
        return products.isEmpty() ? Optional.empty() : Optional.of(products);
    }

    @Override
    public Optional<List<Product>> getAllProducts() {
        var params = new MapSqlParameterSource();
        List<Product> products = jdbcTemplate.query(SQL_GET_ALL_PRODUCTS, params, productMapper);
        return products.isEmpty() ? Optional.empty() : Optional.of(products);
    }

    public boolean deleteProduct(int id){
        var params = new MapSqlParameterSource();
        params.addValue("id", id);
        // Выполняем запрос удаления и получаем количество затронутых строк
        int rowsAffected = jdbcTemplate.update(SQL_DELETE_PRODUCT_BY_ID, params);
        // Если было затронуто хотя бы одна строка, возвращаем true, иначе false
        return rowsAffected > 0;
    }
    //создать продукт
    public Optional<Product> createProduct(Product product){

        var params = createSqlParameterSource(product);

        // Используем KeyHolder для захвата сгенерированного ключа (id)
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(SQL_POST_PRODUCT, params, keyHolder, new String[]{"id"});

        if (rowsAffected > 0) {
            // Получаем сгенерированный id
            Number generatedId = keyHolder.getKey();
            if (generatedId != null) {
                // Возвращаем объект продукта с установленным id
                return getProductById(generatedId.intValue());
            }
        }
        return Optional.empty(); // Вернуть пустой Optional, если продукт не был создан
    }

    @Override
    public Optional<List<Product>> getProductByProductName(String ProductName) {
        var params = new MapSqlParameterSource();
        params.addValue("ProductName", ProductName);
        List<Product> products = jdbcTemplate.query(SQL_GET_PRODUCT_BY_PRODUCT_NAME, params, productMapper);
        return products.isEmpty() ? Optional.empty() : Optional.of(products);
    }

    //в процессе настройки
    public Optional<List<Product>> getProductsByCategoryName(String categoryName) {
        var params = new MapSqlParameterSource();
        params.addValue("categoryName", categoryName);
        List<Product> products = jdbcTemplate.query(SQL_GET_PRODUCTS_BY_CATEGORY_NAME, params, productMapper);
        return products.isEmpty() ? Optional.empty() : Optional.of(products);
    }


    // Приватный метод для создания MapSqlParameterSource из Product
    private MapSqlParameterSource createSqlParameterSource(Product product) {
        var params = new MapSqlParameterSource();
        params.addValue("name", product.name());
        params.addValue("description", product.description());
        params.addValue("link", product.link());
        params.addValue("owner", product.owner());
        params.addValue("contacts", product.contacts());
        params.addValue("category_id", product.category_id());
        params.addValue("status", product.status());
        return params;
    }
}