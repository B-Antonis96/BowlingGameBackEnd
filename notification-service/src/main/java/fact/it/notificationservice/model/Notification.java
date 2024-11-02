package fact.it.notificationservice.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("notification")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String id;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
