package com.example.cryptoapp.service;

import com.example.cryptoapp.entity.EncryptedMessage;
import com.example.cryptoapp.repository.EncryptedMessageRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

@Service
public class CryptoService {

    private final EncryptedMessageRepository repository;

    public CryptoService(EncryptedMessageRepository repository) {
        this.repository = repository;
    }

    private static final String ALGORITHM = "AES";
    private SecretKeySpec secretKey;

    @PostConstruct
    public void init() {
        String key = "MySecretKey12345";
        secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
    }

public String encrypt(String message, String username) throws Exception {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(message.getBytes());
        String result = Base64.getEncoder().encodeToString(encrypted);
        repository.save(new EncryptedMessage(result));
        return result;
    }

    public String decrypt(String encryptedMessage) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decoded = Base64.getDecoder().decode(encryptedMessage);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

    public List<EncryptedMessage> getAllEncryptedMessages() {
        return repository.findAll();
    }
}
