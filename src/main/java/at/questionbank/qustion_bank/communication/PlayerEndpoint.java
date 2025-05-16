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
        return gameSessionManager.findById(request.getGameCode())
                .map(session -> {
                    Player player = Player.builder()
                            .name(request.getPlayerName())
                            .language(request.getLanguage())
                            .score(0)
                            .gameSessionId(session.getId())
                            .avatarUrl(request.getAvatarUrl())
                            .color(request.getColor())
                            .characterId(request.getCharacterId())
                            .online(true)
                            .build();
                    return ResponseEntity.ok(playerManager.joinGame(player));
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/session/{gameSessionId}")
    public List<Player> getBySession(@PathVariable String gameSessionId) {
        return playerManager.getPlayersInGame(gameSessionId);
    }
}
