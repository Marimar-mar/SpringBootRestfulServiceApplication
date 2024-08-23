package com.example.springbootrestfulserviceapplication;

import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorInfo processException(Exception e) {
        logger.error("Unexpected error", e);
        return new ErrorInfo(e.getMessage());
    }

    @ExceptionHandler(NoProductsFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleNoProductsFoundException(NoProductsFoundException e) {
        logger.error("No products found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorInfo("No products found: " + e.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleProductNotFoundException(ProductNotFoundException e) {
        logger.error("Product not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorInfo(e.getMessage()));
    }

    @ExceptionHandler(ProductCreationException.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleProductCreationException(ProductCreationException e) {
        logger.error("Error creating product", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorInfo("Error creating product: " + e.getMessage()));
    }
}

record ErrorInfo(String errorMessage) {
}
