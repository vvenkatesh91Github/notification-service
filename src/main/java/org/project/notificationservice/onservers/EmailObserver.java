package org.project.notificationservice.onservers;

import org.project.notificationservice.events.NotificationEvent;
import org.project.notificationservice.events.PasswordResetEvent;
import org.project.notificationservice.events.UserCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class EmailObserver implements NotificationObserver<NotificationEvent> {

    @Override
    public boolean supports(NotificationEvent event) {
        if (event instanceof UserCreatedEvent e) {
            return e.getEmail() != null;
        }
        if (event instanceof PasswordResetEvent e) {
            return e.getEmail() != null;
        }
        return false;
    }

    @Override
    public void notify(NotificationEvent event) {
        System.out.println("Sending EMAIL for event " + event.getEventType());
    }
}
