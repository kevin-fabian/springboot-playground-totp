package com.fabiankevin.springboottotp.services;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TotpService {
    private final SecretGenerator secretGenerator;
    private final QrGenerator qrGenerator;
    private final DefaultCodeVerifier codeVerifier;

    public TotpService() {
        this.secretGenerator = new DefaultSecretGenerator();
        TimeProvider timeProvider = new SystemTimeProvider();

        CodeGenerator codeGenerator = new DefaultCodeGenerator(HashingAlgorithm.SHA256);
        qrGenerator = new ZxingPngQrGenerator();
        this.codeVerifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        codeVerifier.setTimePeriod(30);
        codeVerifier.setAllowedTimePeriodDiscrepancy(0);
    }

    public byte[] getQrCodeDataUri(String secret) throws QrGenerationException {
        QrData qrData = new QrData.Builder()
                .label("Super Test App")
                .algorithm(HashingAlgorithm.SHA256)
                .secret(secret)
                .issuer("kevin@test.com")
                .digits(6)
                .period(30)
                .build();
        return qrGenerator.generate(qrData);
    }

    public void verifyCode(String secret, String code) {
        boolean validCode = codeVerifier.isValidCode(secret, code);
        log.info("Code is valid: {}", validCode);
        if(!validCode){
            throw new RuntimeException("Invalid code");
        }
    }
}