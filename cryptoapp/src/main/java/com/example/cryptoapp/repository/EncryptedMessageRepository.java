package com.example.cryptoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cryptoapp.entity.EncryptedMessage;

public interface EncryptedMessageRepository extends JpaRepository<EncryptedMessage, Long> {
}
