package fact.it.gameservice.controller;

import fact.it.gameservice.dto.GameRequest;
import fact.it.gameservice.dto.GameResponse;
import fact.it.gameservice.service.GameService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    /**
     * Creates a new game.
     * @param userId the ID of the coupled user to retrieve game
     * @return GameResponse containing the created game's information
     * @throws ConstraintViolationException if validation fails
     */
    @PostMapping("/create/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public GameResponse createGame(@PathVariable Long userId) {
        return gameService.createGame(userId);
    }

    /**
     * Retrieves a game by their ID.
     *
     * @param id the ID of the game to retrieve
     * @return GameResponse containing the game's information
     * @throws ResponseStatusException with HTTP 404 if game is not found
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameResponse getGame(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    /**
     * Retrieves all games.
     *
     * @return List of GameResponse objects containing all games' information
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<GameResponse> getGameStatus() {
        return gameService.getAllGames();
    }

    /**
     * Updates an existing game's information.
     *
     * @param id          the ID of the game to update
     * @param gameRequest DTO containing updated game information
     * @return GameResponse containing the updated game's information
     * @throws ResponseStatusException      with HTTP 404 if game is not found
     * @throws ConstraintViolationException if validation fails
     */
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameResponse updateGame(@PathVariable Long id, @Valid @RequestBody GameRequest gameRequest) {
        return gameService.updateGame(id, gameRequest);
    }

    /**
     * Deletes a game.
     *
     * @param id the ID of the game to delete
     * @throws ResponseStatusException with HTTP 404 if game is not found
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }
}
