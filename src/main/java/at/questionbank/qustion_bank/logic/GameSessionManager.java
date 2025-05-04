package at.questionbank.qustion_bank.logic;

import at.questionbank.qustion_bank.persistence.domain.GameSession;
import at.questionbank.qustion_bank.persistence.domain.Player;
import at.questionbank.qustion_bank.persistence.repository.GameSessionRepository;
import at.questionbank.qustion_bank.persistence.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameSessionManager {

    private final GameSessionRepository gameSessionRepository;
    private final PlayerRepository playerRepository;

    public GameSession create(GameSession gameSession) {
        return gameSessionRepository.save(gameSession);
    }

    public Optional<GameSession> findById(String id) {
        return gameSessionRepository.findById(id);
    }

    public Optional<GameSession> findByRoomName(String roomName) {
        return gameSessionRepository.findByRoomName(roomName);
    }

    public List<GameSession> findAll() {
        return gameSessionRepository.findAll();
    }

    public void deleteAll() {
        gameSessionRepository.deleteAll();
    }
    public List<Player> getPlayersInGame(String gameSessionId) {
        return playerRepository.findByGameSessionId(gameSessionId);
    }
}
