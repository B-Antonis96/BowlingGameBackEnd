package fact.it.gameservice.service;

import fact.it.gameservice.dto.GameRequest;
import fact.it.gameservice.dto.GameResponse;
import fact.it.gameservice.model.Game;
import fact.it.gameservice.repository.GameRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    // private final WebClient webClient;

    /**
     * Creates a new game.
     *
     * @param gameRequest DTO containing game information to be created
     * @return GameResponse containing the created game's information
     * @throws ConstraintViolationException if validation fails
     */
    @Transactional
    public GameResponse createGame(@Valid GameRequest gameRequest) {

        Game game = Game.builder()
                .userId(gameRequest.getUserId())
                .currentTurn(1)
                .totalScore(0)
                .gameFinished(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        game = gameRepository.save(game);

        return mapToResponse(game);
    }

    /**
     * Retrieves a game by their ID.
     *
     * @param id the ID of the game to retrieve
     * @return GameResponse containing the game's information
     * @throws ResponseStatusException with HTTP 404 if game is not found
     */
    @Transactional(readOnly = true)
    public GameResponse getGameById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Game not found with id: " + id));
        return mapToResponse(game);
    }

    /**
     * Retrieves all users in the system.
     *
     * @return List of UserResponse objects containing all users' information
     */
    @Transactional(readOnly = true)
    public List<GameResponse> getAllGames() {
        return gameRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
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
    @Transactional
    public GameResponse updateGame(Long id, GameRequest gameRequest) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Game not found with id: " + id));

        game.setCurrentTurn(gameRequest.getCurrentTurn());
        game.setTotalScore(gameRequest.getTotalScore());
        game.setGameFinished(game.isGameFinished());
        game.setUpdatedAt(LocalDateTime.now());

        gameRepository.save(game);

        return mapToResponse(game);
    }

    /**
     * Deletes a game.
     *
     * @param id the ID of the game to delete
     * @throws ResponseStatusException with HTTP 404 if game is not found
     */
    @Transactional
    public void deleteGame(Long id) {

        if (!gameRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Game not found with id: " + id);
        }

        gameRepository.deleteById(id);
    }

    private GameResponse mapToResponse(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .currentTurn(game.getCurrentTurn())
                .totalScore(game.getTotalScore())
                .gameFinished(game.isGameFinished())
                .build();
    }
}
