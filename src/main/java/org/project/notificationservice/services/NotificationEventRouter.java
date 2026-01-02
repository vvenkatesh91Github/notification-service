package org.project.notificationservice.services;

import lombok.AllArgsConstructor;
import org.project.notificationservice.events.NotificationEvent;
import org.project.notificationservice.handlers.NotificationEventHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationEventRouter {

    private final ObjectMapper objectMapper;
    private final List<NotificationEventHandler> handlers;

    public <T extends NotificationEvent> void route(T event) {
        handlers.stream()
                .filter(h -> h.supports(event.getEventType()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("No handler found"))
                .handle(event);
    }

    @KafkaListener(
            topics = "notification.events",
            groupId = "notification-group"
    )
    public void consume(byte[] payload) {
        try {
            NotificationEvent event = objectMapper
                    .readValue(payload, NotificationEvent.class);
            route(event);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to consume Kafka event", ex);
        }
    }
}

