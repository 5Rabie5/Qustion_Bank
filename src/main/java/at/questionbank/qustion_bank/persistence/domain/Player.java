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
@Document(collection = "players")
public class Player {
    @Id
    public String id;

    public String name;
    public String language;
    public Integer score;
    public String gameSessionId;
}
