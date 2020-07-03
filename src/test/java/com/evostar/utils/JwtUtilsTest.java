package com.evostar.utils;

import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Test;

class JwtUtilsTest extends ApplicationTests {
    @Test
    public void getToken(){
        String token = JwtUtils.createJWTToken(2,120);
        String userId = JwtUtils.verifyJWTToken(token);
        System.out.println(userId);
    }
}