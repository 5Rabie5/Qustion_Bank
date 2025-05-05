package at.questionbank.qustion_bank.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

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

    private String gameSessionId;  // links to GameSession by ID

    // New fields from avatar selection
    private String avatarUrl;      // custom or character image URL
    private String color;          // visual player tag
    private String characterId;    // identifier of selected character
    private boolean online;

}
