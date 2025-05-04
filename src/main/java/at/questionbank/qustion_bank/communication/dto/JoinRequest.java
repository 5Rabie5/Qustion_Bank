package at.questionbank.qustion_bank.communication.dto;


public class JoinRequest {
    private String gameCode;
    private String playerName;
    private String language;
    private String avatarUrl;

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getColor() {
        return color;
    }

    public String getCharacterId() {
        return characterId;
    }

    private String color;
    private String characterId;



    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}