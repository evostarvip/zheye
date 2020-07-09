package com.evostar.controller;

import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import java.util.HashMap;

class QuestionControllerTest extends ApplicationTests {
    private final String token = "token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTQ4OTIxMzUsInVzZXJJZCI6IjEiLCJ2ZXJzaW9uIjoiMS4wIiwiaWF0IjoxNTk0Mjg3MzM1fQ.d5QP5OtvxKn9w4L6PtEvg6SSYDlai1wXG0PnTtL_Bdw";
    @Test
    //发布问题
    public void testAddQuestion(){
        HashMap<String, String> map = new HashMap<>();
        map.put("title","发布title");
        map.put("content","发布content");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie",token);
        HttpEntity<HashMap<String, String>> request = new HttpEntity<>(map,headers);
        ResponseEntity<String> entity = restTemplate.exchange("/question/add", HttpMethod.POST, request, String.class);
        System.out.println(entity.getBody());
        System.out.println(entity.getStatusCode());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    //回答问题
    @Test
    public void testAddAnswer(){
        HashMap<String, String> map = new HashMap<>();
        map.put("qid","1");
        map.put("content","回答问题");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie",token);
        HttpEntity<HashMap<String, String>> request = new HttpEntity<>(map,headers);
        ResponseEntity<String> entity = restTemplate.exchange("/answer/add", HttpMethod.POST, request, String.class);
        System.out.println(entity.getBody());
        System.out.println(entity.getStatusCode());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    //获取问题详情
    public void testGetQuestionDetail(){
        int qid = 1;
        String url = "/question/detail?qid="+qid;
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        System.out.println(entity.getBody());
        System.out.println(entity.getStatusCode());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    //获取回答列表
    public void testAnswerList(){
        int page = 1;
        int qid = 1;
        String url = "/answerList?qid="+qid+"&page="+page;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie",token);
        HttpEntity<MultiValueMap<String,String>> request =  new HttpEntity<>(null,headers);
        ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        System.out.println(entity.getBody());
        System.out.println(entity.getStatusCode());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    //获取回答的详情
    public void testAnswerDetail(){
        int aid = 1;
        String url = "/answer/detail?aid="+aid;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie",token);
        HttpEntity<MultiValueMap<String,String>> request =  new HttpEntity<>(null,headers);
        ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        System.out.println(entity.getBody());
        System.out.println(entity.getStatusCode());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }
}