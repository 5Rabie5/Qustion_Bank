package at.questionbank.qustion_bank.logic;

import at.questionbank.qustion_bank.communication.dto.JoinRequest;
import at.questionbank.qustion_bank.persistence.domain.GameSession;
import at.questionbank.qustion_bank.persistence.domain.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameJoinManager {

    private final PlayerManager playerManager;
    private final GameSessionManager gameSessionManager;

    @MessageMapping("/join")
    @SendTo("/topic/players")
    public Player handleJoin(JoinRequest joinRequest) {
        GameSession session = gameSessionManager.findById(joinRequest.getGameCode())
                .orElse(null);

        if (session == null) {
            System.out.println("⚠️ GameSession not found for code: " + joinRequest.getGameCode());
            return null;
        }


        Player player = new Player();
        player.setName(joinRequest.getPlayerName());
        player.setLanguage(joinRequest.getLanguage());
        player.setScore(0);
        player.setGameSessionId(session.getId());

        player.setAvatarUrl(joinRequest.getAvatarUrl());
        player.setColor(joinRequest.getColor());
        player.setCharacterId(joinRequest.getCharacterId());



        Player saved = playerManager.joinGame(player);
        System.out.println("✅ WebSocket: Player joined - " + saved.getName());

        return saved;
    }
}
