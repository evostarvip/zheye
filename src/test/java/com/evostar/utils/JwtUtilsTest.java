package com.evostar.utils;

import com.auth0.jwt.interfaces.Claim;
import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Test;

import java.util.Map;

class JwtUtilsTest extends ApplicationTests {
    @Test
    public void getToken(){
        String token = JwtUtils.createJWTToken(2,120);
        Map<String, Claim> claim = JwtUtils.verifyJWTToken(token);
        System.out.println(claim.get("userId"));
    }
}