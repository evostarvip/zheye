package com.evostar.controller;

import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

class SupportControllerTest extends ApplicationTests {
    private String token = "token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJTRVJWSUNFIiwiZXhwIjoxNTk0NzAyNjk4LCJ1c2VySWQiOjEsInZlcnNpb24iOiIxLjAiLCJpYXQiOjE1OTQ2OTU0OTh9.RxVVMtsytccbMESXq2xBVBfjudhjIaAHifgw03X9of4";
    @Test
    public void testSupport(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("cookie",token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> entity = restTemplate.exchange("/support?id=1&type=1", HttpMethod.GET, request, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testUnSupport(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("cookie",token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> entity = restTemplate.exchange("/unsupport?id=1&type=1", HttpMethod.GET, request, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }
}