package com.evostar.utils;

import com.auth0.jwt.interfaces.Claim;
import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Test;

import java.util.Map;

class JwtUtilsTest extends ApplicationTests {
    @Test
    public void getToken(){
        //String token = JwtUtils.createJWTToken(2,120);
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTQyMDM0NTksInVzZXJJZCI6IjEiLCJ2ZXJzaW9uIjoiMS4wIiwiaWF0IjoxNTk0MTk2MjU5fQ.DvBrMXldQr-NDPHgbZffqfCdaxskJJ0OMr_jivpa19s";
        Map<String, Claim> claim = JwtUtils.verifyJWTToken(token);
        System.out.println(claim.get("userId").asString());
    }
}