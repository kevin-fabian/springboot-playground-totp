package com.fabiankevin.springboottotp;

import dev.samstevens.totp.secret.DefaultSecretGenerator;
import org.junit.jupiter.api.Test;

class SpringbootTotpApplicationTests {

    @Test
    void contextLoads() {
        DefaultSecretGenerator defaultSecretGenerator = new DefaultSecretGenerator();
        System.out.println(defaultSecretGenerator.generate());
    }

}
