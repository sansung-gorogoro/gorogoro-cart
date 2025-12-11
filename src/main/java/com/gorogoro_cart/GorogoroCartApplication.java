package com.gorogoro_cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GorogoroCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(GorogoroCartApplication.class, args);
    }

}
