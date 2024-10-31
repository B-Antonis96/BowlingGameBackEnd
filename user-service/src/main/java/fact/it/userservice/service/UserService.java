package fact.it.userservice.service;

import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final WebClient webClient;

    public void saveUserScore(String username, int score) {
        User user = User.builder()
                .username(username)
                .score(score)
                .build();
        userRepository.save(user);
    }

    public List<UserResponse> getLeaderboard() {
        return userRepository.findTop10ByOrderByScoreDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .score(user.getScore())
                .build();
    }
}
