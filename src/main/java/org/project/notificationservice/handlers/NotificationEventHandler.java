package org.project.notificationservice.handlers;

import org.project.notificationservice.events.NotificationEvent;

public interface NotificationEventHandler {
    boolean supports(String eventType);
    void handle(NotificationEvent event);
}
