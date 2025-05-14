package at.questionbank.qustion_bank.logic;

import at.questionbank.qustion_bank.communication.dto.JoinRequest;
import at.questionbank.qustion_bank.persistence.domain.GameSession;
import at.questionbank.qustion_bank.persistence.domain.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GameJoinManager {

    private final PlayerManager playerManager;
    private final GameSessionManager gameSessionManager;
    private final SimpMessagingTemplate messagingTemplate;
<<<<<<< HEAD

    @MessageMapping("/join")
    public void handleJoin(JoinRequest joinRequest) {

        GameSession session = gameSessionManager.findById(joinRequest.getGameCode())
                .orElse(null);

        if (session == null) {
            System.out.println("⚠️ GameSession not found for code: " + joinRequest.getGameCode());
            return;
        }

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

        // Update the last activity time for the session
        gameSessionManager.updateLastActivityTime(session.getId());

        List<Player> players = gameSessionManager.getPlayersInGame(session.getId());

        messagingTemplate.convertAndSend("/topic/players/" + joinRequest.getGameCode(), players);
    }
=======
    private final SessionTracker sessionTracker;
//    @MessageMapping("/join")
//    public void handleJoin(JoinRequest joinRequest) {
//
//        GameSession session = gameSessionManager.findById(joinRequest.getGameCode())
//                .orElse(null);
//
//        if (session == null) {
//            System.out.println("⚠️ GameSession not found for code: " + joinRequest.getGameCode());
//            return;
//        }
//
//        Player player = new Player();
//        player.setName(joinRequest.getPlayerName());
//        player.setLanguage(joinRequest.getLanguage());
//        player.setScore(0);
//        player.setGameSessionId(session.getId());
//        player.setAvatarUrl(joinRequest.getAvatarUrl());
//        player.setColor(joinRequest.getColor());
//        player.setCharacterId(joinRequest.getCharacterId());
//        player.setOnline(true);
//        Player saved = playerManager.joinGame(player);
//        System.out.println("✅ WebSocket: Player joined - " + saved.getName());
//
//        List<Player> players = gameSessionManager.getPlayersInGame(session.getId());
//
//        messagingTemplate.convertAndSend("/topic/players/" + joinRequest.getGameCode(), players);
//        String sessionId = SimpAttributesContextHolder.currentAttributes().getSessionId();
//        sessionTracker.registerSession(sessionId, saved.getId());
//    }


>>>>>>> d774e81 ( st03 clean)
}
