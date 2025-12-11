package com.gorogoro_cart.cart.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient(
            WebClient.Builder builder,
            @Value("${feign.client.config.course-service.url}") String courseServiceUrl
    ) {
        return builder.baseUrl(courseServiceUrl).build();
    }
}
