package com.along.springboot;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;

@SpringBootTest
class SpringbootApplicationTests {

    /**
     * 生成安全密钥
     */
    @Test
    void contextLoads() {
        SecretKey build = Jwts.SIG.HS512.key().build();
        String s = DatatypeConverter.printHexBinary(build.getEncoded());
        System.out.println(s);
    }

}
