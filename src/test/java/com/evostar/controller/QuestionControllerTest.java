package com.evostar.controller;

import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.*;

class QuestionControllerTest extends ApplicationTests {
    @Test
    public void testAddQuestion(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/question/add", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        //Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }
}