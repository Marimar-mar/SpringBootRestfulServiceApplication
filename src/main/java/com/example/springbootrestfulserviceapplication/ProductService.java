package com.example.springbootrestfulserviceapplication;

import java.util.List;

public interface ProductService {

    Product getProduct(int productId);
    List<Product> getAllProducts();

    void deleteProduct(int productId);

    Product createProduct(Product product);

    List<Product> getProductsByCategoryName (String categoryName);
}