package at.questionbank.qustion_bank.persistence.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    private String id;

    private String name;
    private String language;
    private int score;

    // Store GameSession by its ID (Mongo style)
    private String gameSessionId;
    private String avatarUrl;
    private String color;
    private String characterId;

}
