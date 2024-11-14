package fact.it.userservice.controller;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.service.UserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Creates a new user.
     *
     * @param userRequest DTO containing user information to be created
     * @return UserResponse containing the created user's information
     * @throws ConstraintViolationException if validation fails
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return UserResponse containing the user's information
     * @throws ResponseStatusException with HTTP 404 if user is not found
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Retrieves all users.
     *
     * @return List of UserResponse objects containing all users' information
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
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
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    /**
     * Deletes a user.
     *
     * @param id the ID of the user to delete
     * @throws ResponseStatusException with HTTP 404 if user is not found
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    /**
     * Retrieves the leaderboard with the top 25 users by high score.
     *
     * @return List of UserResponse objects representing the leaderboard
     */
    @GetMapping("/leaderboard")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getLeaderboard() {
        return userService.getLeaderboard();
    }
}
