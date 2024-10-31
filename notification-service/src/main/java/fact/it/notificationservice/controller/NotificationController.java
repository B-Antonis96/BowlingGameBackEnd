package fact.it.notificationservice.controller;

import fact.it.notificationservice.dto.NotificationRequest;
import fact.it.notificationservice.dto.NotificationResponse;
import fact.it.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/")
    public NotificationResponse createNotification(@RequestBody NotificationRequest request) {
        return notificationService.createNotification(request);
    }

    @GetMapping("/game/{gameId}")
    public List<NotificationResponse> getNotificationsByGameId(@PathVariable Long gameId) {
        return notificationService.getNotificationsByGameId(gameId);
    }
}
