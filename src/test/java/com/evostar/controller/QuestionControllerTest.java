package com.evostar.controller;

import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

class QuestionControllerTest extends ApplicationTests {
    @Test
    public void testAddQuestion(){

        ResponseEntity<String> entity = restTemplate.getForEntity("/question/add", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        //Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }
}