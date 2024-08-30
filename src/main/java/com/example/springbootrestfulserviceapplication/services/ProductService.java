package com.example.springbootrestfulserviceapplication.services;

import com.example.springbootrestfulserviceapplication.models.Product;

import java.util.List;

public interface ProductService {

    Product getProduct(int productId);
    List<Product> getAllProducts(String filter);

    void deleteProduct(int productId);

    Product createProduct(Product product);
    List<Product> getProductByProductName(String ProductName);

    List<Product> getProductsByCategoryName (String categoryName);
}