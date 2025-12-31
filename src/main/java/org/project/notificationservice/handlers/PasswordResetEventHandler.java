package org.project.notificationservice.handlers;

import org.project.notificationservice.events.NotificationEvent;
import org.project.notificationservice.events.PasswordResetEvent;
import org.project.notificationservice.onservers.NotificationObserver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PasswordResetEventHandler
        implements NotificationEventHandler {

    private final List<NotificationObserver<PasswordResetEvent>> observers;

    public PasswordResetEventHandler(List<NotificationObserver<PasswordResetEvent>> observers) {
        this.observers = observers;
    }

    @Override
    public boolean supports(String eventType) {
        return "PASSWORD_RESET".equals(eventType);
    }

    @Override
    public void handle(NotificationEvent event) {
        PasswordResetEvent passwordResetEvent = (PasswordResetEvent) event;
        observers.stream()
                .filter(o -> o.supports(passwordResetEvent))
                .forEach(o -> o.notify(passwordResetEvent));
    }
}

