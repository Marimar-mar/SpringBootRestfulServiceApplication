package com.example.springbootrestfulserviceapplication;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class ProductServiceImpl implements ProductService {//реализует бизнесслогику (внутреннюю логику обработки данных

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(int productId) {
        return productRepository.getProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public List<Product> getAllProducts(String filter) {
        if ("archived".equalsIgnoreCase(filter)) {
            return productRepository.getProductsByStatus("archived")
                    .orElseThrow(() -> new NoProductsFoundException("No archived products found in the database."));
        } else if ("published".equalsIgnoreCase(filter)) {
            return productRepository.getProductsByStatus("published")
                    .orElseThrow(() -> new NoProductsFoundException("No published products found in the database."));
        } else if ("draft".equalsIgnoreCase(filter)) {
            return productRepository.getProductsByStatus("draft")
                    .orElseThrow(() -> new NoProductsFoundException("No draft products found in the database."));
        } else if ("all".equalsIgnoreCase(filter)) {
            return productRepository.getAllProducts()
                    .orElseThrow(() -> new NoProductsFoundException("No products found in the database."));
        } else {
            // По умолчанию возвращаем все активные продукты (не архивированные)
            return productRepository.getProductsByStatus("published")
                    .orElseThrow(() -> new NoProductsFoundException("No published products found in the database."));
        }
    }

    @Override
    public void deleteProduct(int productId){
        if (!productRepository.deleteProduct(productId)) {
            throw new ProductNotFoundException((productId));
        }
    }

    public Product createProduct(Product product){
    return productRepository.createProduct(product)
            .orElseThrow(() -> new ProductCreationException("Failed to create product in the database."));
    }

    public Product getProductByProductName (String ProductName){
        return productRepository.getProductByProductName(ProductName)
                .orElseThrow(() -> new NoProductsFoundException("No products found with product name: " + ProductName));
    }

    public List<Product> getProductsByCategoryName (String categoryName){
        return productRepository.getProductsByCategoryName(categoryName)
                .orElseThrow(() -> new NoProductsFoundException("No products found for category: " + categoryName));
    }
}