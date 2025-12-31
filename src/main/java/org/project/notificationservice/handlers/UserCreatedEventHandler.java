package org.project.notificationservice.handlers;

import org.project.notificationservice.events.NotificationEvent;
import org.project.notificationservice.events.UserCreatedEvent;
import org.project.notificationservice.onservers.NotificationObserver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCreatedEventHandler
        implements NotificationEventHandler {

    private final List<NotificationObserver> observers;

    public UserCreatedEventHandler(
            List<NotificationObserver> observers) {
        this.observers = observers;
    }

    @Override
    public boolean supports(String eventType) {
        return "USER_CREATED".equals(eventType);
    }

    @Override
    public void handle(NotificationEvent event) {
        UserCreatedEvent userEvent = (UserCreatedEvent) event;
        observers.stream()
                .filter(o -> o.supports(userEvent))
                .forEach(o -> o.notify(userEvent));
    }
}

