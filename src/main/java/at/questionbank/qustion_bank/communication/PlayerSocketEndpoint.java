package at.questionbank.qustion_bank.communication;

import at.questionbank.qustion_bank.communication.dto.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
@RequiredArgsConstructor
public class PlayerSocketEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(PlayerSocketEndpoint.class);

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Handles player joining via WebSocket.
     * Expected destination from client: /app/join
     */
    @MessageMapping("/join")
    public void handleJoin(JoinRequest request, SimpMessageHeaderAccessor headers) {
        logger.info("✅ Player joined via WebSocket: {}", request.getPlayerName());

        // Broadcast the new player to others in the same game session
        messagingTemplate.convertAndSend(
                "/topic/players/" + request.getGameCode(),
                request
        );

        logger.info("🧩 Broadcasting to /topic/players/{}", request.getGameCode());
    }

    /**
     * WebSocket connect event handler
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        logger.info("🔌 WebSocket connected: {}", event.getMessage().getHeaders());
    }

    /**
     * WebSocket disconnect event handler
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        logger.info("❌ WebSocket disconnected: sessionId={}", event.getSessionId());
    }

    /**
     * Global STOMP error handler for debugging
     */
    @MessageExceptionHandler
    public void handleError(Throwable exception) {
        logger.error("❗ WebSocket error: {}", exception.getMessage(), exception);
    }
}
