package at.questionbank.qustion_bank.logic;

import at.questionbank.qustion_bank.persistence.domain.Player;
import at.questionbank.qustion_bank.persistence.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
@Component
@RequiredArgsConstructor
public class SessionTracker {

    private final PlayerRepository playerRepository;
    private final GameSessionManager gameSessionManager;

    private final ConcurrentHashMap<String, String> sessionIdToPlayerId = new ConcurrentHashMap<>();

    public void registerSession(String sessionId, String playerId) {
        sessionIdToPlayerId.put(sessionId, playerId);
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        final Logger logger = LoggerFactory.getLogger(SessionTracker.class);
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = sha.getSessionId();
        String playerId = sessionIdToPlayerId.remove(sessionId);

        if (playerId != null) {
            playerRepository.findById(playerId).ifPresent(player -> {
                System.out.println("❌ Disconnected: " + player.getName());
                logger.info("❌ Disconnected: {}", player.getName());
                playerRepository.deleteById(playerId);

                // Clean up game session if empty
                var playersLeft = gameSessionManager.getPlayersInGame(player.getGameSessionId());
                if (playersLeft.isEmpty()) {
                    gameSessionManager.deleteById(player.getGameSessionId());
                    logger.info("🧹 GameSession removed: {}", player.getGameSessionId());
                }
            });
        }
    }
}
