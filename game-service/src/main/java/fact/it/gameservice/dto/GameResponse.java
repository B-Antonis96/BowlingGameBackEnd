package fact.it.gameservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameResponse {
    private Long id;
    // private Long userId;
    private int currentTurn;
    private int totalScore;
    private boolean gameFinished;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
