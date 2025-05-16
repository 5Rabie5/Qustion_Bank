package at.questionbank.qustion_bank.logic;

import at.questionbank.qustion_bank.persistence.domain.Player;
import at.questionbank.qustion_bank.persistence.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerManager {

    private final PlayerRepository playerRepository;

    public Player joinGame(Player player) {
        return playerRepository.save(player); // ✅ already persists to DB
    }

    public List<Player> getPlayersInGame(String gameSessionId) {
        return playerRepository.findByGameSessionId(gameSessionId);
    }

    public Optional<Player> findById(String id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> findByGameSessionIdAndName(String sessionId, String name) {
        return playerRepository.findByGameSessionIdAndName(sessionId, name);
    }

    public void deleteAll() {
        playerRepository.deleteAll();
    }

    @Transactional
    public void deleteByGameSessionId(String gameSessionId) {
        List<Player> players = getPlayersInGame(gameSessionId);
        if (!players.isEmpty()) {
            playerRepository.deleteAll(players);
        }
    }
}
