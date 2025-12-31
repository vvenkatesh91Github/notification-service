package org.project.notificationservice.onservers;

import org.project.notificationservice.events.NotificationEvent;
import org.project.notificationservice.events.UserCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class SMSObserver  implements NotificationObserver<NotificationEvent> {
    @Override
    public boolean supports(NotificationEvent event) {
        if (event instanceof UserCreatedEvent e) {
            return e.getPhoneNumber() != null;
        }
        return false;
    }

    @Override
    public void notify(NotificationEvent event) {
        System.out.println("Sending SMS for event " + event.getEventType());
    }
}
