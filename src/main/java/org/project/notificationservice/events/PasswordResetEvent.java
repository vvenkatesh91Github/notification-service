package org.project.notificationservice.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetEvent implements NotificationEvent {
    private String email;

    @Override
    public String getEventType() {
        return "PASSWORD_RESET";
    }
}
