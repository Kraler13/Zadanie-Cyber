package com.example.cryptoapp.entity;

import jakarta.persistence.*;

@Entity
public class EncryptedMessage  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String encrypted;

    public EncryptedMessage() {}

    public EncryptedMessage(String encrypted) {
        this.encrypted = encrypted;
    }

    public Long getId() {
        return id;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }
}
