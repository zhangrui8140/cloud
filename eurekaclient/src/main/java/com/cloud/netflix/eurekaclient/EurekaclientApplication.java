package com.cloud.netflix.eurekaclient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker
@RefreshScope
public class EurekaclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaclientApplication.class, args);
    }

}

@RefreshScope
@RestController
class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${server.port:default}")
    private String value;


    @RequestMapping("/service-instances/{applicationName}")
    @HystrixCommand(fallbackMethod = "defaultStores")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        System.out.print(value);
        if(value.equals("default")) throw new RuntimeException();
        return this.discoveryClient.getInstances(applicationName);
    }

    public List<ServiceInstance> defaultStores(String param) {
        return Collections.emptyList();
    }


    @RequestMapping("/returnname/{applicationName}")
    @HystrixCommand(fallbackMethod = "defaultName")
    public String returnName() {
        if(value.equals("default")) throw new RuntimeException();
        return value;
    }

    public String defaultName() {
        return "default Hystrix";
    }
}
