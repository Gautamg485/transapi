package com.happy.transapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthCheck {

    @GetMapping("/health")
    public ResponseEntity health() {
        return new ResponseEntity("I am Alive!!!", null, HttpStatus.OK);
    }
}
