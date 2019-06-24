package game;

import javafx.scene.layout.Pane;
import game.GUI.buttons.GameAbandonButton;
import game.GUI.buttons.GameNewButton;

/**
 * All types of Game should be able to be loaded by Main.java
 */
public abstract class Game extends Pane implements GameAbandonButton, GameNewButton {
    private int gameScore;

    // TODO add settings.user.score stuff
    protected Game() {}

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public int getGameScore() {
        return gameScore;
    }
}
