package settings.user.settings;

public enum GameDifficulty {
    EASY("Easy"), NORMAL("Normal"), HARD("Hard");

    private final String name;

    GameDifficulty(String name) {
        this.name = name;
    }

    @Override
    public String toString() { return this.name; }

    public static GameDifficulty fromString(String value) {
        for (GameDifficulty b : GameDifficulty.values()) {
            if (b.name.equalsIgnoreCase(value)) {
                return b;
            }
        }
        return null;
    }
}
