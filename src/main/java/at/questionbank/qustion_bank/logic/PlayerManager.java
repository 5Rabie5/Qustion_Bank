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

    /**
     * Save or join a player to the game.
     */
    public Player joinGame(Player player) {
        return playerRepository.save(player);
    }

    /**
     * Retrieve all players by game session ID.
     */
    public List<Player> getPlayersInGame(String gameSessionId) {
        return playerRepository.findByGameSessionId(gameSessionId);
    }

    /**
     * Find player by ID.
     */
    public Optional<Player> findById(String id) {
        return playerRepository.findById(id);
    }

    /**
     * Delete all players (global).
     */
    public void deleteAll() {
        playerRepository.deleteAll();
    }

    /**
     * Delete all players in a specific game session.
     */
    @Transactional
    public void deleteByGameSessionId(String gameSessionId) {
        List<Player> players = getPlayersInGame(gameSessionId);
        if (!players.isEmpty()) {
            playerRepository.deleteAll(players);
        }
    }
}
