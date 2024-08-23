package com.example.springbootrestfulserviceapplication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {//за взаимодействие с фронтом

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/{productId:\\d+}")
    public ResponseEntity<Product> getProduct(@PathVariable int productId) {
        Product product = productService.getProduct(productId);
        return ResponseEntity.ok(product); // Возвращаем ResponseEntity с статусом 200 OK и телом ответа
    }
//проверка git
    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products); // Возвращаем ResponseEntity с статусом 200 OK и телом ответа
    }

    @DeleteMapping (value = "/{productId:\\d+}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new ApiResponse("Product archived successfully")); // Возвращаем ResponseEntity с статусом 200 OK и сообщением
    }

    @PostMapping ("/")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request){
        Product productToCreate = new Product(0, request.name(), request.description(), request.link(), request.owner(), request.contacts());
        Product createdProduct = productService.createProduct(productToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct); // Возвращаем ResponseEntity с статусом 201 CREATED и телом добавленного продукта
    }
}