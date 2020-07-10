package com.evostar.controller;


import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;

import javax.swing.text.html.parser.Entity;
import java.util.HashMap;

@Controller
class LoginControllerTest extends ApplicationTests {
    @Test
    //注册
    public void testReg(){
        HashMap<String, String> map = new HashMap<>();
        map.put("username","zhangSan77");
        map.put("password","123456");
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/reg", map, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void testLogin(){
        //三个参数，username、password、rememberme
        HashMap<String, String> map = new HashMap<>();
        map.put("username","zhangSan");
        map.put("password","123456");
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/login", map, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testLayout(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/layout", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

}