# Notification Service

Notification Service is a Spring Boot microservice designed to handle notifications in an event-driven architecture. It listens to events from other microservices (such as User Service) and sends notifications based on event type and user data (email, phone number).

## Key Features
- **Supports multiple event types**: Handles events like `UserCreatedEvent`, `PasswordResetEvent`, and more.
- **Observer pattern implementation**: Flexible notification handlers using the observer pattern for easy extension.
- **Extensible notification channels**: Easily add new channels such as Email, SMS, or Push notifications.
- **Polymorphic event deserialization**: Supports scalable communication between microservices by deserializing different event types.

## Architecture
- **Event-Driven**: Listens for domain events from other services (e.g., via message broker or REST hooks).
- **Observer Pattern**: Notification handlers (observers) are registered for specific event types, allowing flexible and decoupled notification logic.
- **Extensibility**: New notification channels or event types can be added with minimal changes to existing code.
- **Polymorphic Deserialization**: Events are deserialized based on type, supporting scalable and maintainable event processing.

## Setup Instructions
### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build and Run
1. Clone the repository:
   ```bash
   git clone <repo-url>
   cd notification-service
   ```
2. Build the project:
   ```bash
   ./mvnw clean install
   ```
3. Run the service:
   ```bash
   ./mvnw spring-boot:run
   ```

### Configuration
- Edit `src/main/resources/application.properties` to configure notification channels, message broker, etc.

## Usage
- The service listens for events such as `UserCreatedEvent` and `PasswordResetEvent`.
- Upon receiving an event, it routes the event to the appropriate notification handler(s) (e.g., Email, SMS).
- Example: When a new user is created, a `UserCreatedEvent` triggers an email and/or SMS notification.

#### Example Event Payload
```json
{
  "userId": "12345",
  "email": "user@example.com",
  "phone": "+1234567890"
}
```

## API Endpoints
- (Post a notification using, `/notify`)

## Extending the Service
- **Add a new event type**: Create a new event class in `events/` and a corresponding handler in `handlers/`.
- **Add a new notification channel**: Implement a new observer in `onservers/` and register it in the event router or handler.