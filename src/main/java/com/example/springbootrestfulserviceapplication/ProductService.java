package com.example.springbootrestfulserviceapplication;

import java.util.List;

public interface ProductService {

    Product getProduct(int productId);
    List<Product> getAllProducts(String filter);

    void deleteProduct(int productId);

    Product createProduct(Product product);
    Product getProductByProductName (String ProductName);

    List<Product> getProductsByCategoryName (String categoryName);
}