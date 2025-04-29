package at.questionbank.qustion_bank.communication;

import at.questionbank.qustion_bank.logic.PlayerManager;
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

    @PostMapping("/join")
    public ResponseEntity<Player> join(@RequestBody Player player) {
        player.setScore(0);
        Player saved = playerManager.joinGame(player);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/session/{gameSessionId}")
    public List<Player> getBySession(@PathVariable String gameSessionId) {
        return playerManager.getPlayersInGame(gameSessionId);
    }
}
