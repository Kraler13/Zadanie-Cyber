package com.example.cryptoapp.entity;

import jakarta.persistence.*;

@Entity
public class EncryptedMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public EncryptedMessage(String encrypted, User user) {
    this.encrypted = encrypted;
    this.user = user;
}
    private String encrypted;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public EncryptedMessage() {
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
