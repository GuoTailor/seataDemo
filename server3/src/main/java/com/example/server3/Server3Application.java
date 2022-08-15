package com.example.server3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.example")
@MapperScan("com.example.server3.mapper")
public class Server3Application {

    public static void main(String[] args) {
        SpringApplication.run(Server3Application.class, args);
    }

}
