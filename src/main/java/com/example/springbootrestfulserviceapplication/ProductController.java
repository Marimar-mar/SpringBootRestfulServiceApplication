package com.example.springbootrestfulserviceapplication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

@CrossOrigin
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

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(value = "filter", required = false) String filter) {
        List<Product> products = productService.getAllProducts(filter);
        return ResponseEntity.ok(products); // Возвращаем ResponseEntity с статусом 200 OK и телом ответа
    }


    @DeleteMapping (value = "/{productId:\\d+}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new ApiResponse("Product archived successfully")); // Возвращаем ResponseEntity с статусом 200 OK и сообщением
    }


    @PostMapping ("/")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request){
        Product productToCreate = new Product(0, request.name(), request.description(), request.link(), request.owner(), request.contacts(), request.category_id(), "draft");
        Product createdProduct = productService.createProduct(productToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct); // Возвращаем ResponseEntity с статусом 201 CREATED и телом добавленного продукта
    }

    @GetMapping("/productName")
    public ResponseEntity<List<Product>> getProductByName (@RequestParam("name") String Name) {
        String decodedName = "";
        try {
            decodedName = URLDecoder.decode(Name, "UTF-8");
        } catch (Exception e){
        }
        List<Product> products = productService.getProductByProductName(decodedName);
        return  ResponseEntity.ok(products);
    }

    @GetMapping("/categoryName")
    public ResponseEntity<List<Product>> getProductsByCategoryName (@RequestBody GetProductByCategoryNameRequest request){
        List<Product> products = productService.getProductsByCategoryName(request.categoryName());
        return ResponseEntity.ok(products);
    }
}