package fact.it.notificationservice.service;

import fact.it.notificationservice.dto.NotificationRequest;
import fact.it.notificationservice.dto.NotificationResponse;
import fact.it.notificationservice.model.Notification;
import fact.it.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationResponse createNotification(NotificationRequest request) {
        Notification notification = Notification.builder()
                .gameId(request.getGameId())
                .message(request.getMessage())
                .build();

        notification = notificationRepository.save(notification);
        return mapToResponse(notification);
    }

    public List<NotificationResponse> getNotificationsByGameId(Long gameId) {
        return notificationRepository.findAll().stream()
                .filter(notification -> notification.getGameId().equals(gameId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .gameId(notification.getGameId())
                .message(notification.getMessage())
                .build();
    }
}
