package com.example.springbootrestfulserviceapplication;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts()
                .orElseThrow(() -> new NoProductsFoundException("No products found in the database."));
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
}