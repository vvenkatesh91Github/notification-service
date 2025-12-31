package org.project.notificationservice.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "eventType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserCreatedEvent.class, name = "USER_CREATED"),
        @JsonSubTypes.Type(value = PasswordResetEvent.class, name = "PASSWORD_RESET")
})
public interface NotificationEvent {
    String getEventType();
}
