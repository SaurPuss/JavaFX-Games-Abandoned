package settings.user.settings;

import javafx.scene.control.ToggleButton;

public enum GameDifficulty {
    EASY("Easy"), NORMAL("Normal"), HARD("Hard");

    private final String name;

    GameDifficulty(String name) {
        this.name = name;
    }

    @Override
    public String toString() { return this.name; }

    public static GameDifficulty fromString(String difficulty) {
        for (GameDifficulty b : GameDifficulty.values()) {
            if (b.name.equalsIgnoreCase(difficulty)) {
                return b;
            }
        }

        return NORMAL;
    }
}
