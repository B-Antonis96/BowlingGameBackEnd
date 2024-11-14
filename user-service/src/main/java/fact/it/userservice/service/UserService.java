package fact.it.userservice.service;

import fact.it.userservice.dto.UserLoginRequest;
import fact.it.userservice.dto.UserLoginResponse;
import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    // private final WebClient webClient;

    /**
     * Creates a new user.
     *
     * @param userLoginRequest DTO containing user information to be created
     * @return UserResponse containing the created user's information
     * @throws ConstraintViolationException if validation fails
     */
    @Transactional
    public UserLoginResponse createUser(@Valid UserLoginRequest userLoginRequest) {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByUsername(userLoginRequest.getUsername()));

        if (existingUser.isPresent()) {
            return UserLoginResponse.builder()
                    .id(existingUser.get().getId())
                    .username(existingUser.get().getUsername())
                    .build();
        }

        User user = User.builder()
                .username(userLoginRequest.getUsername())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        user = userRepository.save(user);

        return UserLoginResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return UserResponse containing the user's information
     * @throws ResponseStatusException with HTTP 404 if user is not found
     */
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with id: " + id));
        return mapToResponse(user);
    }

    /**
     * Retrieves all users.
     *
     * @return List of UserResponse objects containing all users' information
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing user's information.
     *
     * @param id          the ID of the user to update
     * @param userRequest DTO containing updated user information
     * @return UserResponse containing the updated user's information
     * @throws ResponseStatusException      with HTTP 404 if user is not found
     * @throws ConstraintViolationException if validation fails
     */
    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with id: " + id));

        user.setUsername(userRequest.getUsername());
        user.setHighScore(userRequest.getHighScore());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return mapToResponse(user);
    }

    /**
     * Deletes a user.
     *
     * @param id the ID of the user to delete
     * @throws ResponseStatusException with HTTP 404 if user is not found
     */
    @Transactional
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }

    /**
     * Retrieves the top 10 users based on high scores.
     *
     * @return List of UserResponse objects representing the leaderboard
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getLeaderboard() {
        return userRepository.findTop10ByHighScoreOrderByHighScoreDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Maps a User entity to a UserResponse DTO.
     *
     * @param user the User entity to map
     * @return UserResponse containing the user's information
     */
    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .highScore(user.getHighScore())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
