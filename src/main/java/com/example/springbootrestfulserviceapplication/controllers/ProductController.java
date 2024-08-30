package com.example.springbootrestfulserviceapplication.controllers;

import com.example.springbootrestfulserviceapplication.models.Product;
import com.example.springbootrestfulserviceapplication.payload.request.CreateProductRequest;
import com.example.springbootrestfulserviceapplication.payload.request.GetProductByCategoryNameRequest;
import com.example.springbootrestfulserviceapplication.payload.response.ApiResponse;
import com.example.springbootrestfulserviceapplication.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/products")
public class ProductController {//за взаимодействие с фронтом

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Следующие три эндпоинта для проверки наличия прав доступа у текущего пользователя
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        GetUserInfo();
        return "User Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        GetUserInfo();
        return "Admin Board.";
    }

    @GetMapping("/anybody")
    public String anyAccess() {
        GetUserInfo();
        return "Anybody Board.";
    }

    private void GetUserInfo()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Получаем роли пользователя
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // Выводим роли в консоль
        if (authorities.isEmpty())
        {
            System.out.println("No Roles");
        }
        else {
            System.out.println("Have Roles:");
        }
        authorities.forEach(authority -> System.out.println("Role: " + authority.getAuthority() + ", name = " + authentication.getName()));
    }

    // Далее реальный функционал
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

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/productName")
    public ResponseEntity<List<Product>> getProductByName (@RequestParam("name") String Name) {
        String decodedName;
        try {
            decodedName = URLDecoder.decode(Name, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
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