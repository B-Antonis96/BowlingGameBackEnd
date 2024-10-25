package fact.it.userservice.repository;

import fact.it.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findTop10ByOrderByScoreDesc(); // Fetches top 10 scores
}
