package at.questionbank.qustion_bank;

import at.questionbank.qustion_bank.persistence.domain.GameSession;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameSessionTest {

    @Test
    public void testBuilderAndGetters() {
        GameSession session = GameSession.builder()
                .id("game123")
                .roomName("Test Room")
                .winPoints(500)
                .playerCount(4)
                .categories(new String[]{"science", "history"})
                .difficulty("Medium")
                .gameMode("Fastest Answer")
                .questionType("Multiple Choice")
                .timerMode("Wait All Players")
                .secondsPerQuestion(30)
                .createdAt("2025-05-15T12:00:00Z")
                .build();

        session.setLastActivityTime(1234567890L);

        assertThat(session.getId()).isEqualTo("game123");
        assertThat(session.getRoomName()).isEqualTo("Test Room");
        assertThat(session.getWinPoints()).isEqualTo(500);
        assertThat(session.getPlayerCount()).isEqualTo(4);
        assertThat(session.getCategories()).containsExactly("science", "history");
        assertThat(session.getDifficulty()).isEqualTo("Medium");
        assertThat(session.getGameMode()).isEqualTo("Fastest Answer");
        assertThat(session.getQuestionType()).isEqualTo("Multiple Choice");
        assertThat(session.getTimerMode()).isEqualTo("Wait All Players");
        assertThat(session.getSecondsPerQuestion()).isEqualTo(30);
        assertThat(session.getCreatedAt()).isEqualTo("2025-05-15T12:00:00Z");
        assertThat(session.getLastActivityTime()).isEqualTo(1234567890L);
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        GameSession session = new GameSession();
        session.setRoomName("Empty Session");
        session.setPlayerCount(2);
        session.setLastActivityTime(42L);

        assertThat(session.getRoomName()).isEqualTo("Empty Session");
        assertThat(session.getPlayerCount()).isEqualTo(2);
        assertThat(session.getLastActivityTime()).isEqualTo(42L);
    }
}
