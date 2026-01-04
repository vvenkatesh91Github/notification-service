package org.project.notificationservice.services;

import lombok.AllArgsConstructor;
import org.project.notificationservice.events.NotificationEvent;
import org.project.notificationservice.handlers.NotificationEventHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import tools.jackson.core.exc.StreamReadException;
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
    public void consume(byte[] payload, Acknowledgment acknowledgment,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        try {
            NotificationEvent event = objectMapper
                    .readValue(payload, NotificationEvent.class);
            route(event);
            acknowledgment.acknowledge();
            /**
             * spring.kafka.consumer.enable-auto-commit=false in application.properties
             * acknowledgment.acknowledge();
             * For manual offset management, uncomment the line above and
             * By this way we control the retry mechanism, if don't want the event published to DLT
             * Use cases like Poison Message (where the event itself is malformed and cannot be processed like the first catch block)
             */
        } catch(StreamReadException e) {
            acknowledgment.acknowledge();
            System.out.println("Failed to consume Kafka event. TOPIC:" + topic + ", PARTITION:" + partition + ", " +
                    "REASON: Invalid Event for notification, " + objectMapper.readValue(payload, String.class));
        } catch (Exception ex) {
            throw new RuntimeException("Failed to consume Kafka event. TOPIC:" + topic + ", PARTITION:" + partition , ex);
        }
    }
}

