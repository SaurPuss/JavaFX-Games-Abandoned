package game;

import javafx.scene.layout.Pane;

/**
 * All types of Game should be able to be loaded by Main.java
 */
public abstract class Game extends Pane {
    // TODO add settings.score stuff
    protected Game() {}

    public void restartGame() {}
}
