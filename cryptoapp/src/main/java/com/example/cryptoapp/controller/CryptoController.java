package com.example.cryptoapp.controller;

import com.example.cryptoapp.entity.EncryptedMessage;
import com.example.cryptoapp.dto.*;
import com.example.cryptoapp.service.CryptoService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

@RestController
@RequestMapping("/api/crypto")
@CrossOrigin
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @PostMapping("/encrypt")
    public ResponseEntity<EncryptResponse> encrypt(@Valid @RequestBody EncryptRequest request,
            @AuthenticationPrincipal UserDetails user) throws Exception {
        String encrypted = cryptoService.encrypt(request.getMessage(), user.getUsername());
        return ResponseEntity.ok(new EncryptResponse(encrypted));
    }

    @PostMapping("/decrypt")
    public ResponseEntity<String> decrypt(@RequestBody DecryptRequest request) throws Exception {
        String decrypted = cryptoService.decrypt(request.getEncrypted());
        return ResponseEntity.ok(decrypted);
    }

    @GetMapping("/my")
    public ResponseEntity<List<EncryptedMessage>> getMyEncryptedMessages(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(cryptoService.getMessagesForUser(user.getUsername()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EncryptedMessage>> getMessagesForUser(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(cryptoService.getMessagesForUser(user.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        cryptoService.deleteMessage(id, user.getUsername());
        return ResponseEntity.ok().build();
    }

}
