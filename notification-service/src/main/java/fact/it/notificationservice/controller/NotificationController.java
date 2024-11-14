package fact.it.notificationservice.controller;

import fact.it.notificationservice.dto.NotificationRequest;
import fact.it.notificationservice.dto.NotificationResponse;
import fact.it.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    /**
     * Creates a new notification.
     *
     * @param request The notification request containing the message
     * @return NotificationResponse containing the created notification details
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationResponse createNotification(@Valid @RequestBody NotificationRequest request) {
        return notificationService.createNotification(request);
    }

    /**
     * Retrieves a notification by its ID.
     *
     * @param id The ID of the notification to retrieve
     * @return NotificationResponse containing the notification details
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NotificationResponse getNotification(@PathVariable String id) {
        return notificationService.getNotification(id);
    }

    /**
     * Retrieves all notifications.
     *
     * @return List of NotificationResponse objects containing all notifications
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationResponse> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    /**
     * Updates a notification by its ID.
     *
     * @param id      The ID of the notification to update
     * @param request The notification request containing the updated message
     * @return NotificationResponse containing the updated notification details
     */
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NotificationResponse updateNotification(@PathVariable String id, @Valid @RequestBody NotificationRequest request) {
        return notificationService.updateNotification(id, request);
    }

    /**
     * Deletes a notification by its ID.
     *
     * @param id The ID of the notification to delete
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNotification(@PathVariable String id) {
        notificationService.deleteNotification(id);
    }
}
