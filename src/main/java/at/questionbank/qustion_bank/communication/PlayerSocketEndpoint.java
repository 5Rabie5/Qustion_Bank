package at.questionbank.qustion_bank.communication;

import at.questionbank.qustion_bank.communication.dto.JoinRequest;
import at.questionbank.qustion_bank.logic.GameSessionManager;
import at.questionbank.qustion_bank.logic.SessionTracker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PlayerSocketEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(PlayerSocketEndpoint.class);

    private final SimpMessagingTemplate messagingTemplate;
    private final SessionTracker sessionTracker;

    @MessageMapping("/join")
    public void handleJoin(JoinRequest request, SimpMessageHeaderAccessor headerAccessor) {
        logger.info("🚀 Received JoinRequest: {}", request);
        logger.info("🧠 Headers: {}", headerAccessor.toMap());

        // Safeguard from nulls
        if (request == null || request.getGameCode() == null || request.getPlayerName() == null) {
            logger.error("❌ Invalid join request: missing required fields");
            return;
        }

        sessionTracker.registerPlayerFromHeader(headerAccessor, request);

        messagingTemplate.convertAndSend("/topic/players/" + request.getGameCode(), request);
    }



//    @MessageMapping("/join")
//    public void handleJoin(JoinRequest request, SimpMessageHeaderAccessor headerAccessor) {
//        logger.info("✅ Player joined: {}", request.getPlayerName());
//
//        // Register session to track disconnects
//        sessionTracker.registerPlayerFromHeader(headerAccessor, request);
//
//        // Notify other players in the same game session
//        messagingTemplate.convertAndSend("/topic/players/" + request.getGameCode(), request);
//    }


    @MessageExceptionHandler
    public void handleException(Throwable t) {
        logger.error("🔥 WebSocket message handling failed: {}", t.getMessage(), t);
    }

    }
