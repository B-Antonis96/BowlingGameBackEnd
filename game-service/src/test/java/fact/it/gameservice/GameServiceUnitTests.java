package fact.it.gameservice;

import fact.it.gameservice.dto.GameRequest;
import fact.it.gameservice.dto.GameResponse;
import fact.it.gameservice.model.Game;
import fact.it.gameservice.repository.GameRepository;
import fact.it.gameservice.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceUnitTests {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    private Game testGame;
    private GameRequest testGameRequest;
    private final Long TEST_ID = 1L;
    private final Long TEST_USER_ID = 100L;

    @BeforeEach
    void setUp() {
        // Initialize test data
        testGame = Game.builder()
                .id(TEST_ID)
                .userId(TEST_USER_ID)
                .currentTurn(1)
                .totalScore(0)
                .gameFinished(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testGameRequest = GameRequest.builder()
                .userId(TEST_USER_ID)
                .currentTurn(1)
                .totalScore(0)
                .gameFinished(false)
                .build();
    }

    @Test
   public void createGame_Success() {
        // Arrange
        when(gameRepository.save(any(Game.class))).thenReturn(testGame);

        // Act
        GameResponse response = gameService.createGame(testGameRequest);

        // Assert
        assertNotNull(response);
        assertEquals(TEST_ID, response.getId());
        assertEquals(TEST_USER_ID, response.getUserId());
        assertEquals(1, response.getCurrentTurn());
        assertEquals(0, response.getTotalScore());
        assertFalse(response.isGameFinished());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    public void getGameById_Success() {
        // Arrange
        when(gameRepository.findById(TEST_ID)).thenReturn(Optional.of(testGame));

        // Act
        GameResponse response = gameService.getGameById(TEST_ID);

        // Assert
        assertNotNull(response);
        assertEquals(TEST_ID, response.getId());
        assertEquals(TEST_USER_ID, response.getUserId());
        verify(gameRepository, times(1)).findById(TEST_ID);
    }

    @Test
    public void getGameById_FailureIfNotFound() {
        // Arrange
        when(gameRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> gameService.getGameById(TEST_ID));
        verify(gameRepository, times(1)).findById(TEST_ID);
    }

    @Test
    public void getAllGames_Success() {
        // Arrange
        Game secondGame = Game.builder()
                .id(2L)
                .userId(101L)
                .currentTurn(2)
                .totalScore(100)
                .gameFinished(false)
                .build();
        when(gameRepository.findAll()).thenReturn(Arrays.asList(testGame, secondGame));

        // Act
        List<GameResponse> responses = gameService.getAllGames();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    public void updateGame_Success() {
        // Arrange
        when(gameRepository.findById(TEST_ID)).thenReturn(Optional.of(testGame));
        when(gameRepository.save(any(Game.class))).thenReturn(testGame);

        GameRequest updateRequest = GameRequest.builder()
                .userId(TEST_USER_ID)
                .currentTurn(2)
                .totalScore(100)
                .gameFinished(true)
                .build();

        // Act
        GameResponse response = gameService.updateGame(TEST_ID, updateRequest);

        // Assert
        assertNotNull(response);
        assertEquals(TEST_ID, response.getId());
        assertEquals(2, response.getCurrentTurn());
        assertEquals(100, response.getTotalScore());
        verify(gameRepository, times(1)).findById(TEST_ID);
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    public void updateGame_FailureIfNotFound() {
        // Arrange
        when(gameRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class,
                () -> gameService.updateGame(TEST_ID, testGameRequest));
        verify(gameRepository, times(1)).findById(TEST_ID);
        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test
    public void deleteGame_Success() {
        // Arrange
        when(gameRepository.existsById(TEST_ID)).thenReturn(true);
        doNothing().when(gameRepository).deleteById(TEST_ID);

        // Act
        gameService.deleteGame(TEST_ID);

        // Assert
        verify(gameRepository, times(1)).existsById(TEST_ID);
        verify(gameRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    public void deleteGame_FailureIfNotFound() {
        // Arrange
        when(gameRepository.existsById(TEST_ID)).thenReturn(false);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> gameService.deleteGame(TEST_ID));
        verify(gameRepository, times(1)).existsById(TEST_ID);
        verify(gameRepository, never()).deleteById(any());
    }
}