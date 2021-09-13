package com.project99x.priceengine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    Logger logger = LoggerFactory.getLogger(HealthController.class);

    @GetMapping()
    public String healthCheck(){

        logger.info("Health Controller accessed : health check");
        return "API is working...";
    }
}
