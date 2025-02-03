package com.stamp.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/api/v1")
@RestController
public class HealthCheckController {
    @GetMapping("/healthCheck")
    public String healthCheck() {
        LocalDateTime now = LocalDateTime.now();
        return "server is Up! requested at:" + now;
    }
}
