package com.example.springbootrestfulserviceapplication;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {//за взаимодействие с бд

    Optional<Product> getProductById(int id);

    Optional<List<Product>> getProductsByStatus(String status);//возврат по статусу
    Optional<List<Product>> getAllProducts();

    boolean deleteProduct(int Id);

    Optional<Product> createProduct(Product product);

    Optional<List<Product>> getProductByProductName(String ProductName);//возврат по имени продукта

    Optional<List<Product>> getProductsByCategoryName (String category);
}