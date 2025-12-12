package com.gorogoro_cart;

import com.gorogoro_cart.cart.common.messaging.config.MessagingProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableConfigurationProperties(
        MessagingProps.class
)
@EnableFeignClients
@SpringBootApplication
public class GorogoroCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(GorogoroCartApplication.class, args);
    }

}
