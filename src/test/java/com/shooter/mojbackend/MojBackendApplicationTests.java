package com.shooter.mojbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class MojBackendApplicationTests {

    @Test
    public void testPwd(){
        String md5DigestAsHex = DigestUtils.md5DigestAsHex("shooter12345678".getBytes());
        System.out.println(md5DigestAsHex);
    }

}
