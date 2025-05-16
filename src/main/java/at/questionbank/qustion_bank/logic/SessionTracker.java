package at.questionbank.qustion_bank.logic;

import at.questionbank.qustion_bank.communication.dto.JoinRequest;
import at.questionbank.qustion_bank.persistence.domain.Player;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class SessionTracker {

    private static final Logger logger = LoggerFactory.getLogger(SessionTracker.class);

    private final PlayerManager playerManager;
    private final GameSessionManager gameSessionManager;

    private final ConcurrentHashMap<String, String> sessionIdToPlayerId = new ConcurrentHashMap<>();

    /**
     * Registers the player session mapping from WebSocket headers and join request.
     *
     * @param accessor SimpMessageHeaderAccessor to extract session ID
     * @param request  JoinRequest containing player name and game code
     */
    public void registerPlayerFromHeader(SimpMessageHeaderAccessor accessor, JoinRequest request) {
        String sessionId = accessor.getSessionId();
        String playerName = request.getPlayerName();

        if (sessionId == null) {
            logger.error("Session ID is null in registerPlayerFromHeader");
            return;
        }
        if (playerName == null) {
            logger.error("Player name is null in registerPlayerFromHeader");
            return;
        }

        logger.info("📌 Registering session: sessionId={} for player={}", sessionId, playerName);

        playerManager.findByGameSessionIdAndName(request.getGameCode(), playerName).ifPresentOrElse(
                player -> {
                    String playerId = player.getId();
                    if (playerId == null) {
                        logger.error("❌ Player ID is null for player object: {}", player);
                    } else {
                        sessionIdToPlayerId.put(sessionId, playerId);
                        logger.info("✅ Session mapping stored: sessionId={} -> playerId={}", sessionId, playerId);
                    }
                },
                () -> logger.warn("⚠️ Player not found for gameCode={} and name={}", request.getGameCode(), playerName)
        );
    }

    /**
     * Handles cleanup on WebSocket session disconnect.
     *
     * @param event SessionDisconnectEvent
     */
    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        if (sessionId == null) {
            logger.warn("SessionDisconnectEvent with null sessionId");
            return;
        }

        String playerId = sessionIdToPlayerId.remove(sessionId);
        if (playerId == null) {
            logger.warn("⚠️ No player found for sessionId={}", sessionId);
            return;
        }

        playerManager.findById(playerId).ifPresent(player -> {
            logger.info("❌ Player disconnected: {}", player.getName());
            playerManager.deleteByGameSessionId(player.getGameSessionId());

            var playersLeft = gameSessionManager.getPlayersInGame(player.getGameSessionId());
            if (playersLeft.isEmpty()) {
                gameSessionManager.deleteById(player.getGameSessionId());
                logger.info("🧹 Cleaned up empty game session: {}", player.getGameSessionId());
            }
        });
    }
}
