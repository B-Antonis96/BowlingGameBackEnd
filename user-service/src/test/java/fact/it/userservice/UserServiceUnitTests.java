package fact.it.userservice;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import fact.it.userservice.service.UserService;
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
public class UserServiceUnitTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserRequest testUserRequest;
    private LocalDateTime now;
    private static final Long TEST_ID = 1L;
    private static final String TEST_USERNAME = "John Doe";
    private static final int TEST_HIGH_SCORE = 100;

    @BeforeEach
    void setUp() {
        // Initialize test data
        now = LocalDateTime.now();
        testUser = User.builder()
                .id(TEST_ID)
                .username(TEST_USERNAME)
                .highScore(TEST_HIGH_SCORE)
                .createdAt(now)
                .updatedAt(now)
                .build();

        testUserRequest = UserRequest.builder()
                .username(TEST_USERNAME)
                .highScore(TEST_HIGH_SCORE)
                .build();
    }

    //region Create
//    @Test
//    public void testCreateUser_Success() {
//        // Arrange
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//        // Act
//        UserResponse response = userService.createUser(testUserRequest);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(TEST_ID, response.getId());
//        assertEquals(TEST_USERNAME, response.getUsername());
//        verify(userRepository, times(1)).save(any(User.class));
//    }
    //endregion

    //region Read
    @Test
    public void testGetUserById_Success() {
        // Arrange
        when(userRepository.findById(TEST_ID)).thenReturn(Optional.of(testUser));

        // Act
        UserResponse response = userService.getUserById(TEST_ID);

        // Assert
        assertNotNull(response);
        assertEquals(TEST_USERNAME, response.getUsername());
        verify(userRepository, times(1)).findById(TEST_ID);
    }

    @Test
    public void testGetUserById_FailureIfNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> userService.getUserById(TEST_ID));
        verify(userRepository, times(1)).findById(TEST_ID);
    }

    @Test
    public void testGetAllUsers_Success() {
        // Arrange
        List<User> users = Arrays.asList(testUser, testUser.toBuilder().id(2L).build());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserResponse> responses = userService.getAllUsers();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
        verify(userRepository, times(1)).findAll();
    }
    //endregion

    //region Update
    @Test
    public void testUpdateUser_Success() {
        // Arrange
        User updatedUser = testUser.toBuilder()
                .username("Tom Thompson")
                .highScore(150)
                .build();
        when(userRepository.findById(TEST_ID)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserRequest updateRequest = UserRequest.builder()
                .username("Tom Thompson")
                .highScore(150)
                .build();

        // Act
        UserResponse response = userService.updateUser(TEST_ID, updateRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Tom Thompson", response.getUsername());
        assertEquals(150, response.getHighScore());
        verify(userRepository, times(1)).findById(TEST_ID);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUser_FailureIfNotFound() {
        // Arrange
        when(userRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        UserRequest updateRequest = UserRequest.builder()
                .username("Tom Thompson")
                .highScore(150)
                .build();

        // Act & Assert
        assertThrows(ResponseStatusException.class,
                () -> userService.updateUser(TEST_ID, updateRequest));

        // Verify that findById was called but save was never called
        verify(userRepository, times(1)).findById(TEST_ID);
        verify(userRepository, never()).save(any(User.class));
    }
    //endregion

    //region Delete
    @Test
    public void testDeleteUser_Success() {
        // Arrange
        when(userRepository.existsById(TEST_ID)).thenReturn(true);
        doNothing().when(userRepository).deleteById(TEST_ID);

        // Act & Assert
        assertDoesNotThrow(() -> userService.deleteUser(TEST_ID));
        verify(userRepository, times(1)).existsById(TEST_ID);
        verify(userRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    public void testDeleteUser_FailureIfNotFound() {
        // Arrange
        when(userRepository.existsById(TEST_ID)).thenReturn(false);

        // Act & Assert
        assertThrows(ResponseStatusException.class,
                () -> userService.deleteUser(TEST_ID));
        verify(userRepository, times(1)).existsById(TEST_ID);
        verify(userRepository, never()).deleteById(any());
    }
    //endregion
}