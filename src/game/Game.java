package game;

import javafx.scene.layout.Pane;

/**
 * All types of Game should be able to be loaded by Main.java
 */
public abstract class Game extends Pane /*implements GameAbandonButton, GameNewButton*/ {
    protected int gameScore;

    // TODO add settings.user.score stuff
    protected Game() { /* Session.activeGame = true; */ }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }


}
