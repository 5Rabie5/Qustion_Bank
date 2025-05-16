package at.questionbank.qustion_bank.communication.dto;
import lombok.Data;

@Data
public class JoinRequest {
    private String gameCode;
    private String playerName;
    private String language;
    private String avatarUrl;
    private String color;
    private String characterId;
}