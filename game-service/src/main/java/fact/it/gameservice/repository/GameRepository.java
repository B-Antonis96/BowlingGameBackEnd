package fact.it.gameservice.repository;

import fact.it.gameservice.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
