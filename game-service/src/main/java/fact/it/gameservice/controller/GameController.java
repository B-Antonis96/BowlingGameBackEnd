package fact.it.gameservice.controller;

import fact.it.gameservice.dto.GameRequest;
import fact.it.gameservice.dto.GameResponse;
import fact.it.gameservice.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/start")
    public GameResponse startNewGame() {
        return gameService.startNewGame();
    }

    @PostMapping("/{gameId}/throw")
    public GameResponse recordThrow(@PathVariable Long gameId, @RequestBody GameRequest gameRequest) {
        return gameService.recordThrow(gameId, gameRequest);
    }

    // Get current game status as a GameResponse DTO
    @GetMapping("/{gameId}")
    public GameResponse getGameStatus(@PathVariable Long gameId) {
        return gameService.getGameStatus(gameId);
    }
}
