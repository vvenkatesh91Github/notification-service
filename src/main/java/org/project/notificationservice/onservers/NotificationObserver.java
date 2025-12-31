package org.project.notificationservice.onservers;

import org.project.notificationservice.events.NotificationEvent;

public interface NotificationObserver<T extends NotificationEvent> {
    boolean supports(T event);
    void notify(T event);
}
