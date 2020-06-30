package com.evostar;

import com.evostar.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTest {
    @Autowired
    private JwtUtils jwtUtils;
    @Test
    public void getToken(){
        String token = JwtUtils.createJWTToken(2,120);
        String userId = JwtUtils.verifyJWTToken(token);
        System.out.println(userId);
    }
}
