package org.project.notificationservice.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreatedEvent implements NotificationEvent {
    private Long userId;
    private String email;
    private String phoneNumber;

    @Override
    public String getEventType() {
        return "USER_CREATED";
    }
}
