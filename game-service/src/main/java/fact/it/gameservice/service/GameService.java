package fact.it.gameservice.service;

import fact.it.gameservice.dto.GameRequest;
import fact.it.gameservice.dto.GameResponse;
import fact.it.gameservice.model.Game;
import fact.it.gameservice.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final WebClient webClient;

    public GameResponse startNewGame() {
        Game game = Game.builder()
                .currentTurn(1)
                .totalScore(0)
                .gameFinished(false)
                .build();

        game = gameRepository.save(game);

        return mapToGameResponse(game);
    }

    // Record a throw and return updated game state as a GameResponse DTO
    public GameResponse recordThrow(Long gameId, GameRequest gameRequest) {
        Game game = gameRepository.findById(gameId).orElseThrow();

        // Basic scoring logic; adjust with real game rules
        game.setTotalScore(game.getTotalScore() + gameRequest.getPinsKnockedDown());
        game.setCurrentTurn(game.getCurrentTurn() + 1);

        // End game after 10 turns
        if (game.getCurrentTurn() > 10) {
            game.setGameFinished(true);
        }

        game = gameRepository.save(game);
        return mapToGameResponse(game);
    }

    public GameResponse getGameStatus(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        return mapToGameResponse(game);
    }

    private GameResponse mapToGameResponse(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .currentTurn(game.getCurrentTurn())
                .totalScore(game.getTotalScore())
                .gameFinished(game.isGameFinished())
                .build();
    }
}
