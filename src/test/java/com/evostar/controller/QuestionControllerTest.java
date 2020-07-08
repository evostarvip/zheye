package com.evostar.controller;

import com.evostar.ApplicationTests;
import com.evostar.model.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class QuestionControllerTest extends ApplicationTests {
    @Test
    public void testAddQuestion(){

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("title","发布title");
        map.add("content","发布content");
        ResponseEntity<String> entity = restTemplate.postForEntity("/question/add", map, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGetDetail(){
        int qid = 1;
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies =new ArrayList<String>();
        cookies.add("token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTQwMTQ0NDMsInVzZXJJZCI6IjMiLCJ2ZXJzaW9uIjoiMS4wIiwiaWF0IjoxNTk0MDA3MjQzfQ.c-7g3u1rW_L9XUR5ZH2E-CT8xTOtc2wQYAKlfvUwi8g; Path=/; HttpOnly");       //在 header 中存入cookies
        headers.put(HttpHeaders.COOKIE,cookies);

        ResponseEntity<Map> entity = restTemplate.exchange("/question/detail?qid="+qid, HttpMethod.GET, new HttpEntity<String>(headers), Map.class);
        System.out.println(entity.getBody());
    }
}