package at.questionbank.qustion_bank.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "game_sessions")
public class GameSession {
    @Id
    public String id;

    public String roomName;
    public Integer winPoints;
    public Integer playerCount;
    public String[] categories;
    public String difficulty;
    public String gameMode;
    public String questionType;
    public String timerMode;
    public Integer secondsPerQuestion;
    public String createdAt;
}
