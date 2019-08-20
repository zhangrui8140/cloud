package com.cloud.routing;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RoutingController {

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/home")
    public String getHome() {

        String data = restTemplate.getForObject("http://eurekaClient/returnname/eurekaClient", String.class);
        return data;

    }
}
