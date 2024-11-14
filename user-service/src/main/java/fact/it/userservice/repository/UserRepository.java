package fact.it.userservice.repository;

import fact.it.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u ORDER BY u.highScore DESC LIMIT 10")
    List<User> findTop10ByHighScoreOrderByHighScoreDesc();

    User findByUsername(String username);

    boolean existsByUsername(String username);
}
