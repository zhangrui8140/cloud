package com.cloud.circuitbreaker;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker
@RefreshScope
@EnableHystrixDashboard
public class CircuitbreakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CircuitbreakerApplication.class, args);
    }

}


@RefreshScope
@RestController
@EnableHystrixDashboard
class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;


    //region 测试熔断

    @RequestMapping("/service-instances/{applicationName}")
    @HystrixCommand(fallbackMethod = "defaultStores")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        int i= new Random().nextInt(10);
        System.out.println("---------------------"+i);
        if(i<6) throw new RuntimeException();
        return this.discoveryClient.getInstances(applicationName);
    }

    public List<ServiceInstance> defaultStores(String param) {
        return Collections.emptyList();
    }

    //endregion

}
