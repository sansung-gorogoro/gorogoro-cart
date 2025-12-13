package com.gorogoro_cart.cart.infrastructure.event;

import com.gorogoro_cart.cart.application.port.in.HandleUserDeletedUseCase;
import com.gorogoro_cart.cart.application.port.in.event.UserDeletedEvent;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDeletedEventListener {
    private final HandleUserDeletedUseCase handleUserDeletedUseCase;

    @RabbitListener(queues = "${rabbit.events.queues.user-deleted.name}", ackMode = "MANUAL")
    public void handleUserDeletedEvent(UserDeletedEvent payload, Message message, Channel channel) {
        try {
            log.info("Received UserDeletedEvent: {}", payload);
            handleUserDeletedUseCase.handleUserDeleted(payload);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("Failed to process UserDeletedEvent: {}", payload, e);
            basicNackSilently(channel, message);
        }
    }

    private void basicNackSilently(Channel channel, Message message) {
        try {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        } catch (Exception ex) {
            log.error("Failed to nack UserDeletedEvent: {}", message, ex);
        }
    }
}
