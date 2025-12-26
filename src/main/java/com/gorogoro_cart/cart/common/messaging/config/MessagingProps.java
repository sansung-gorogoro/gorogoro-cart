package com.gorogoro_cart.cart.common.messaging.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbit.events")
public record MessagingProps(
        String exchange,
        String routingPrefix,
        Queues queues,
        DeadLetters dlq,
        Retry retry
) {
    public record Retry(
            String exchange,
            String queue,
            String routing,
            Integer ttlMs
    ) {
    }

    /**
     * 라우팅키 생성 헬퍼. 접두사가 정해져 있으면 붙이고, 없으면 이벤트 타입을 그대로 사용.
     */
    public String toRoutingKey(String eventType) {
        if (routingPrefix == null || routingPrefix.isBlank()) {
            return eventType;
        }
        return routingPrefix.endsWith(".") ? routingPrefix + eventType : routingPrefix;
    }

    public record Queues(
            QueueProps courseDeleted,
            QueueProps userDeleted
    ) {
    }

    public record QueueProps(
            String name,
            List<String> bindings
    ) {
    }

    public record DeadLetters(
            String dlx,
            String name,
            String routing
    ) {
    }
}
