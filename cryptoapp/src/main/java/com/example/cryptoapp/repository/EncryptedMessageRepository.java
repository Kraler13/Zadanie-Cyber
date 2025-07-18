package com.example.cryptoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cryptoapp.entity.EncryptedMessage;
import java.util.List;

public interface EncryptedMessageRepository extends JpaRepository<EncryptedMessage, Long> {
    List<EncryptedMessage> findAllByUserUsername(String username);
}
