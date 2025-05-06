package at.questionbank.qustion_bank.logic;

import at.questionbank.qustion_bank.persistence.domain.GameSession;
import at.questionbank.qustion_bank.persistence.domain.Player;
import at.questionbank.qustion_bank.persistence.repository.GameSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameSessionManager {

    private final GameSessionRepository gameSessionRepository;
    private final PlayerManager playerManager;

    public GameSession create(GameSession gameSession) {
        System.out.println("🕹️ Creating new game session: " + gameSession.getRoomName());
        // Set the initial last activity time to the current time
        gameSession.setLastActivityTime(System.currentTimeMillis());
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
        return playerManager.getPlayersInGame(gameSessionId);
    }

    public void deleteById(String gameSessionId) {
        playerManager.deleteByGameSessionId(gameSessionId);
        gameSessionRepository.deleteById(gameSessionId);
    }

    public void delete(GameSession session) {
        playerManager.deleteByGameSessionId(session.getId());
        gameSessionRepository.delete(session);
    }

    // Update last activity time for a session
    public void updateLastActivityTime(String gameSessionId) {
        GameSession session = gameSessionRepository.findById(gameSessionId)
                .orElseThrow(() -> new RuntimeException("Game session not found"));
        session.setLastActivityTime(System.currentTimeMillis());
        gameSessionRepository.save(session);
    }

    // Scheduled job to clean up inactive sessions
    @Scheduled(fixedDelay = 300_000) // every 5 minutes
    public void cleanInactiveGames() {
        long timeout = 30 * 60 * 1000; // 30 minutes timeout
        List<GameSession> sessions = findAll();
        for (GameSession session : sessions) {
            // Check if the session is inactive for more than the timeout
            if (System.currentTimeMillis() - session.getLastActivityTime() > timeout) {
                delete(session);
                System.out.println("🕒 Deleted inactive game session: " + session.getId());
            } else {
                System.out.println("💬 Active game session not deleted: " + session.getId());
            }
        }
    }
}
