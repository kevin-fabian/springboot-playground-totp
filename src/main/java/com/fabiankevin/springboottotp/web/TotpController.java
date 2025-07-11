package com.fabiankevin.springboottotp.web;

import com.fabiankevin.springboottotp.services.TotpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/totp")
@Slf4j
public class TotpController {
    // hardcoded secret for demonstration purposes
    private String secret = "OMJP4MQHF7MRWJ4ROUIFP5GJKTVRTKQK";

    private final TotpService totpService;

    @PostMapping("/verify")
    public void verifyTotp(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");
        log.info("Verifying TOTP code: {}", code);
        totpService.verifyCode(secret, code);
    }

    @GetMapping(value = "/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQrCodeImage() {
        try {
            byte[] qrCodeBytes = totpService.getQrCodeDataUri(secret);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCodeBytes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}