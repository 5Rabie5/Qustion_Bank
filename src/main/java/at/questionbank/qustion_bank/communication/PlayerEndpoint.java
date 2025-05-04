package at.questionbank.qustion_bank.communication;

import at.questionbank.qustion_bank.communication.dto.JoinRequest;
import at.questionbank.qustion_bank.logic.GameSessionManager;
import at.questionbank.qustion_bank.logic.PlayerManager;
import at.questionbank.qustion_bank.persistence.domain.GameSession;
import at.questionbank.qustion_bank.persistence.domain.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerEndpoint {
    private final PlayerManager playerManager;
    private final GameSessionManager gameSessionManager;
    @PostMapping("/join")
    public ResponseEntity<Player> join(@RequestBody JoinRequest request) {
        GameSession session = gameSessionManager.findById(request.getGameCode())
                .orElse(null);
        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        Player player = new Player();
        player.setName(request.getPlayerName());
        player.setLanguage(request.getLanguage());
        player.setScore(0);
        player.setGameSessionId(session.getId()); // Mongo-style
        Player saved = playerManager.joinGame(player);
        return ResponseEntity.ok(saved);
    }
    @GetMapping("/session/{gameSessionId}")
    public List<Player> getBySession(@PathVariable String gameSessionId) {
        return playerManager.getPlayersInGame(gameSessionId);
    }
}
