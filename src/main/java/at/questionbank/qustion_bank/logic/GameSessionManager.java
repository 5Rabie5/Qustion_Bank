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

    @Scheduled(fixedDelay = 300_000) // every 5 minutes
    public void cleanInactiveGames() {
        List<GameSession> sessions = findAll();
        for (GameSession session : sessions) {
            // Check if there are active players before deleting
            if (playerManager.getPlayersInGame(session.getId()).isEmpty()) {
                delete(session); // use internal logic
                System.out.println("🕒 Deleted inactive game session: " + session.getId());
            } else {
                System.out.println("💬 Active game session not deleted: " + session.getId());
            }
        }
    }
}
