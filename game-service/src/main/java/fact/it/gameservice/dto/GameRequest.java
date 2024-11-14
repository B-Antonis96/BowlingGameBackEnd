package fact.it.gameservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameRequest {
    private Long userId;
    private int currentTurn;
    private int totalScore;
    private boolean gameFinished;
}
