package org.project.notificationservice.services;

import org.project.notificationservice.events.NotificationEvent;
import org.project.notificationservice.handlers.NotificationEventHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationEventRouter {

    private final List<NotificationEventHandler> handlers;

    public NotificationEventRouter(List<NotificationEventHandler> handlers) {
        this.handlers = handlers;
    }

    public <T extends NotificationEvent> void route(T event) {
        handlers.stream()
                .filter(h -> h.supports(event.getEventType()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("No handler found"))
                .handle(event);
    }
}

