package com.example.cryptoapp.controller;

import com.example.cryptoapp.entity.EncryptedMessage;
import com.example.cryptoapp.dto.*;
import com.example.cryptoapp.service.CryptoService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<EncryptResponse> encrypt(@RequestBody EncryptRequest request,
                                               @AuthenticationPrincipal UserDetails user) throws Exception {
    String encrypted = cryptoService.encrypt(request.getMessage(), user.getUsername());
    return ResponseEntity.ok(new EncryptResponse(encrypted));
}

    @PostMapping("/decrypt")
    public ResponseEntity<String> decrypt(@RequestBody DecryptRequest request) throws Exception {
        String decrypted = cryptoService.decrypt(request.getEncrypted());
        return ResponseEntity.ok(decrypted);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EncryptedMessage>> getAllEncryptedMessages() {
    return ResponseEntity.ok(cryptoService.getAllEncryptedMessages());
}
}
