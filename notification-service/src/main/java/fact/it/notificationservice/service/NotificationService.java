package fact.it.notificationservice.service;

import fact.it.notificationservice.dto.NotificationRequest;
import fact.it.notificationservice.dto.NotificationResponse;
import fact.it.notificationservice.model.Notification;
import fact.it.notificationservice.repository.NotificationRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
// @Validated
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    /**
     * Creates a new notification in the system.
     *
     * @param request The notification request containing the message
     * @return NotificationResponse containing the created notification details
     * @throws ConstraintViolationException if the request fails validation
     */
    @Transactional
    public NotificationResponse createNotification(@Valid NotificationRequest request) {

        Notification notification = Notification.builder()
                .message(request.getMessage())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        notification = notificationRepository.save(notification);

        return mapToResponse(notification);
    }

    /**
     * Retrieves a notification by its ID.
     *
     * @param id The ID of the notification to retrieve
     * @return NotificationResponse containing the notification details
     * @throws ResponseStatusException with HTTP Status NOT_FOUND if notification is not found
     */
    public NotificationResponse getNotification(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Notification not found with id: " + id));
        return mapToResponse(notification);
    }

    /**
     * Retrieves all notifications.
     *
     * @return List of NotificationResponse objects containing all notifications
     * @apiNote This method is optimized for read-only operations and uses a read-only transaction
     */
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates a notification.
     *
     * @param id The ID of the notification to update
     * @param request The notification request containing the updated message
     * @return NotificationResponse containing the updated notification details
     * @throws ResponseStatusException with HTTP Status NOT_FOUND if notification is not found
     * @throws ConstraintViolationException if the request fails validation
     */
    @Transactional
    public NotificationResponse updateNotification(String id, @Valid NotificationRequest request) {

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Notification not found with id: " + id));

        notification.setMessage(request.getMessage());
        notification.setUpdatedAt(LocalDateTime.now());

        notification = notificationRepository.save(notification);

        return mapToResponse(notification);
    }

    /**
     * Deletes a notification.
     *
     * @param id The ID of the notification to delete
     * @throws ResponseStatusException with HTTP Status NOT_FOUND if notification is not found
     */
    @Transactional
    public void deleteNotification(String id) {

        if (!notificationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Notification not found with id: " + id);
        }

        notificationRepository.deleteById(id);
    }

    /**
     * Maps a Notification entity to a NotificationResponse DTO.
     *
     * @param notification The Notification entity to be mapped
     * @return NotificationResponse containing the mapped notification data
     */
    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }
}
