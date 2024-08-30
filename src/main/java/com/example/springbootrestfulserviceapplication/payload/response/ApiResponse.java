package com.example.springbootrestfulserviceapplication.payload.response;

public class ApiResponse {
        private String message;

        public ApiResponse(String message) {
            this.message = message;
        }

        // Геттер для получения сообщения
        public String getMessage() {
            return message;
        }

        // Сеттер для изменения сообщения, если нужно
        public void setMessage(String message) {
            this.message = message;
        }
}
