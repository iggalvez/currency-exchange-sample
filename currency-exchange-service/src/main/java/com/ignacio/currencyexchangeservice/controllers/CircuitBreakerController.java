package com.in28minutes.microservices.currencyexchangeservice.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CircuitBreakerController {
    Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
    @GetMapping("/sample-api")
    //@Retry(name = "sample-api",fallbackMethod = "hardcodedResponse")
    //@CircuitBreaker(name = "default",fallbackMethod = "hardcodedResponse")
    @RateLimiter(name = "default")
    @Bulkhead(name = "default")
    public String sampleAp() {
        logger.info("sample API call received");
       // ResponseEntity<String> response = new RestTemplate().
        //getForEntity("http://loacalhost:8080/some-dummy-url",
          //              String.class);
        //return response.getBody().toString();
        return "sample API";
    }
    public String hardcodedResponse(Exception ex){
        return "fallback-response";
    }
}


