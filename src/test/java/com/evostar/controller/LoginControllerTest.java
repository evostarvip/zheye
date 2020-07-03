package com.evostar.controller;

import com.evostar.ApplicationTests;
import com.evostar.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

class LoginControllerTest extends ApplicationTests {
    @Test
    //注册
    public void testReg(){
        //三个参数，username、password、rememberme
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username","zhangSan");
        map.add("password","123456");
        ResponseEntity<String> entity = restTemplate.postForEntity("/reg/", map, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void testLogin(){
        //三个参数，username、password、rememberme
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username","zhangSan");
        map.add("password","1234567");
        ResponseEntity<String> entity = restTemplate.postForEntity("/login/", map, String.class);

        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

}