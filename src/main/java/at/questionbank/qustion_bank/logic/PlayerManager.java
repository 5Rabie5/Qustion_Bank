package at.questionbank.qustion_bank.logic;

import at.questionbank.qustion_bank.persistence.domain.Player;
import at.questionbank.qustion_bank.persistence.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerManager {

    private final PlayerRepository playerRepository;

    public Player joinGame(Player player) {
        return playerRepository.save(player);
    }

    public List<Player> getPlayersInGame(String gameSessionId) {
        return playerRepository.findByGameSessionId(gameSessionId);
    }

    public Optional<Player> findById(String id) {
        return playerRepository.findById(id);
    }

    public void deleteAll() {
        playerRepository.deleteAll();
    }
}
