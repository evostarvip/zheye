package com.evostar.controller;

import com.evostar.ApplicationTests;
import com.evostar.model.User;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

@Controller
class LoginControllerTest extends ApplicationTests {
    @Test
    //注册
    public void testReg(){
        //三个参数，username、password、rememberme
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username","zhangSan77");
        map.add("password","123456");
        ResponseEntity<String> entity = restTemplate.postForEntity("/reg", map, String.class);
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
        String m = JSONObject.toJSONString(map);
        ResponseEntity<String> entity = restTemplate.postForEntity("/login", m, String.class);
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

}