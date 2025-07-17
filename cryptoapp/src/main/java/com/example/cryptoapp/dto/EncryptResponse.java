package com.example.cryptoapp.dto;

public class EncryptResponse {
    private String encrypted;
    public EncryptResponse(String encrypted) { this.encrypted = encrypted; }
    public String getEncrypted() { return encrypted; }
}
