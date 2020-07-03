package com.evostar.controller;

import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


class HomeControllerTest extends ApplicationTests {
    @Test
    public void testIndex(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/index", String.class);
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

}