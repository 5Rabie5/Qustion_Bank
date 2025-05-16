package at.questionbank.qustion_bank.communication;

import at.questionbank.qustion_bank.logic.GameSessionManager;
import at.questionbank.qustion_bank.persistence.domain.GameSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/game-sessions")
@RequiredArgsConstructor
public class GameSessionEndpoint {

    private final GameSessionManager gameSessionManager;

    @PostMapping
    public ResponseEntity<GameSession> create(@RequestBody GameSession session) {
        session.setCreatedAt(LocalDateTime.now().toString());
        return ResponseEntity.ok(gameSessionManager.create(session));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameSession> getById(@PathVariable String id) {
        return gameSessionManager.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<GameSession> getAll() {
        return gameSessionManager.findAll();
    }
}
