package at.questionbank.qustion_bank.persistence.repository;

import at.questionbank.qustion_bank.persistence.domain.GameSession;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GameSessionRepository extends MongoRepository<GameSession, String> {
    Optional<GameSession> findByRoomName(String roomName);
}
