package fact.it.gameservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "games")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private int currentTurn;
    private int totalScore;
    private boolean gameFinished;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
