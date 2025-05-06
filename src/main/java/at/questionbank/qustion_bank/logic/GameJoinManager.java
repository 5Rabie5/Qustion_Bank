package at.questionbank.qustion_bank.logic;
import at.questionbank.qustion_bank.Exception.GameNotFoundException;
import at.questionbank.qustion_bank.communication.dto.JoinRequest;
import at.questionbank.qustion_bank.persistence.domain.GameSession;
import at.questionbank.qustion_bank.persistence.domain.Player;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpAttributesContextHolder;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GameJoinManager {

    private final PlayerManager playerManager;
    private final GameSessionManager gameSessionManager;
    private final SimpMessagingTemplate messagingTemplate;
    private final SessionTracker sessionTracker;
    @MessageMapping("/join")
    public void handleJoin(JoinRequest joinRequest) {
         final Logger logger = LoggerFactory.getLogger(GameJoinManager.class);

//        GameSession session = gameSessionManager.findById(joinRequest.getGameCode())
//                .orElse(null);
        GameSession session = gameSessionManager.findById(joinRequest.getGameCode())
                .orElseThrow(() -> new GameNotFoundException("Game session with code " + joinRequest.getGameCode() + " not found."));




        Player player = new Player();
        player.setName(joinRequest.getPlayerName());
        player.setLanguage(joinRequest.getLanguage());
        player.setScore(0);
        player.setGameSessionId(session.getId());
        player.setAvatarUrl(joinRequest.getAvatarUrl());
        player.setColor(joinRequest.getColor());
        player.setCharacterId(joinRequest.getCharacterId());
        player.setOnline(true);
        Player saved = playerManager.joinGame(player);
        System.out.println("✅ WebSocket: Player joined - " + saved.getName());

        List<Player> players = gameSessionManager.getPlayersInGame(session.getId());

        messagingTemplate.convertAndSend("/topic/players/" + joinRequest.getGameCode(), players);
        String sessionId = SimpAttributesContextHolder.currentAttributes().getSessionId();
        sessionTracker.registerSession(sessionId, saved.getId());
        // Log the successful join
        logger.info("✅ WebSocket: Player {} joined game session {}", joinRequest.getPlayerName(), joinRequest.getGameCode());

    }


}
