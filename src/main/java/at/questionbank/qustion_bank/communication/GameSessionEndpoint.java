package at.questionbank.qustion_bank.communication;

import at.questionbank.qustion_bank.logic.GameSessionManager;
import at.questionbank.qustion_bank.persistence.domain.GameSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/game-sessions")
@RequiredArgsConstructor
public class GameSessionEndpoint {

    private final GameSessionManager gameSessionManager;

    @PostMapping
    public ResponseEntity<GameSession> create(@RequestBody GameSession session) {
        session.setCreatedAt(LocalDateTime.now().toString());
        GameSession saved = gameSessionManager.create(session);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameSession> getById(@PathVariable String id) {
        return gameSessionManager.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    public List<GameSession> getAll() {
        return gameSessionManager.findAll();
    }
}
