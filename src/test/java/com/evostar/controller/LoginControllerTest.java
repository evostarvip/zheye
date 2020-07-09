package com.evostar.controller;


import com.evostar.ApplicationTests;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;

@Controller
class LoginControllerTest extends ApplicationTests {
    @Test
    //注册
    public void testReg(){
        /*UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("127.0.0.1:8080").
                path("/test").build(true);
        URI uri = uriComponents.toUri();
        JSONObject param = new JSONObject();
        param.put()

        RequestEntity<JSONObject> requestEntity = RequestEntity.post(uri).
                //添加cookie(这边有个问题，假如我们要设置cookie的生命周期，作用域等参数我们要怎么操作)
                        header(HttpHeaders.COOKIE,"key1=value1").
                //添加header
                        header(("MyRequestHeader", "MyValue").
        accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).
                body(requestParam);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(requestEntity,JSONObject.class);
        JSONObject responseEntityBody = responseEntity.getBody();*/



        /*MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username","zhangSan77");
        map.add("password","123456");
        ResponseEntity<String> entity = restTemplate.postForEntity("/reg", map, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(),HttpStatus.OK);*/
    }

    @Test
    public void testLogin(){
        //三个参数，username、password、rememberme
        HashMap<String, String> map = new HashMap<>();
        map.put("username","zhangSan");
        map.put("password","123456");
        String m = JSONObject.toJSONString(map);
        System.out.println(m);
        ResponseEntity<String> entity = restTemplate.postForEntity("/login", m, String.class);
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

}