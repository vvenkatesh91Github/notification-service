package org.project.notificationservice.controllers;

import lombok.AllArgsConstructor;
import org.project.notificationservice.events.NotificationEvent;
import org.project.notificationservice.services.NotificationEventRouter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notify")
@AllArgsConstructor
public class NotificationController {
    private final NotificationEventRouter router;

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Notification Service is up and running");
    }

    @PostMapping
    public ResponseEntity<Void> receiveEvent(
            @RequestBody NotificationEvent event) {

        router.route(event);
        return ResponseEntity.ok().build();
    }
}
