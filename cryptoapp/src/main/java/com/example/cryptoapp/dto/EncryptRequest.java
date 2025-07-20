package com.example.cryptoapp.dto;

import jakarta.validation.constraints.NotBlank;

public class EncryptRequest {

    @NotBlank(message = "Wiadomość nie może być pusta")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
