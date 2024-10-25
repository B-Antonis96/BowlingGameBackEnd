package fact.it.notificationservice.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("notification")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String id;
    private String message;
    private Long gameId;
}
