package com.example.springbootrestfulserviceapplication;

import java.util.List;

public interface ProductService {

    Product getProduct(int productId);
    List<Product> getAllProducts();

    void deleteProduct(int productId);

    public Product createProduct(Product product);
}