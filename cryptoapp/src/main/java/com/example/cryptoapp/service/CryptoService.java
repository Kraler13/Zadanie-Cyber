package com.example.cryptoapp.service;

import com.example.cryptoapp.entity.EncryptedMessage;
import com.example.cryptoapp.entity.User;
import com.example.cryptoapp.repository.EncryptedMessageRepository;
import com.example.cryptoapp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import org.owasp.encoder.Encode;

@Service
public class CryptoService {

    private final EncryptedMessageRepository repository;
    private final UserRepository userRepository;

    public CryptoService(EncryptedMessageRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    private static final String ALGORITHM = "AES";
    private SecretKeySpec secretKey;

    @PostConstruct
    public void init() {
        String key = "MySecretKey12345";
        secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
    }

    public String encrypt(String message, String username) throws Exception {
    String sanitized = Encode.forHtmlContent(message);

    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encrypted = cipher.doFinal(sanitized.getBytes());
    String result = Base64.getEncoder().encodeToString(encrypted);

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found"));

    repository.save(new EncryptedMessage(result, user));
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

    public List<EncryptedMessage> getMessagesForUser(String username) {
        return repository.findAllByUserUsername(username);
    }

    public void deleteMessage(Long id, String username) {
        EncryptedMessage message = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (!message.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Not authorized to delete this message");
        }

        repository.delete(message);
    }
}
