package fact.it.userservice.controller;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/save-score")
    public void saveUserScore(@RequestBody UserRequest userRequest) {
        userService.saveUserScore(userRequest.getUsername(), userRequest.getScore());
    }

    @GetMapping("/leaderboard")
    public List<UserResponse> getLeaderboard() {
        return userService.getLeaderboard();
    }
}
