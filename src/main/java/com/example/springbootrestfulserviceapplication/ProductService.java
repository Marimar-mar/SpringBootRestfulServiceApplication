package com.example.springbootrestfulserviceapplication;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product getProduct(int productId);
    List<Product> getAllProducts(String filter);

    void deleteProduct(int productId);

    Product createProduct(Product product);
    List<Product> getProductByProductName(String ProductName);

    List<Product> getProductsByCategoryName (String categoryName);
}