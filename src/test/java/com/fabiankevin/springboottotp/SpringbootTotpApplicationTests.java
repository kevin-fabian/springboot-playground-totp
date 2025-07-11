package com.fabiankevin.springboottotp;

import dev.samstevens.totp.secret.DefaultSecretGenerator;
import org.apache.commons.codec.binary.Base32;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

class SpringbootTotpApplicationTests {

    @Test
    void contextLoads() {
        DefaultSecretGenerator defaultSecretGenerator = new DefaultSecretGenerator();
        System.out.println(defaultSecretGenerator.generate());

        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        System.out.println(base32.encodeToString(bytes));
    }

}
