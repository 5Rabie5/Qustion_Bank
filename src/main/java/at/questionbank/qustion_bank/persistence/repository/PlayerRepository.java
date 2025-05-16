package at.questionbank.qustion_bank.persistence.repository;

import at.questionbank.qustion_bank.persistence.domain.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends MongoRepository<Player, String> {
    List<Player> findByGameSessionId(String gameSessionId);

    @Override
    Optional<Player> findById(String s);

    // In PlayerRepository.java
    Optional<Player> findByGameSessionIdAndName(String gameSessionId, String name);

}
