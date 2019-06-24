package game.GUI.panes;

import game.GUI.buttons.GameAbandonButton;
import game.GUI.buttons.GameNewButton;
import game.GUI.buttons.GameResetButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class BottomBarPane extends HBox implements GameAbandonButton, GameResetButton, GameNewButton {

    public BottomBarPane() {
        setPadding(new Insets(10,0,10,0));
        setAlignment(Pos.CENTER);
        getChildren().addAll(resetGame(), abandonGame(), newGame());
    }

    // TODO Make it so you can have multiple options for the bottom bar pane results
    public BottomBarPane(int type) {
        setPadding(new Insets(10,0,10,0));
        setAlignment(Pos.CENTER);
        switch(type) {
            case 0: getChildren().addAll(resetGame(), abandonGame()); break;
            case 1: getChildren().addAll(newGame()); break;
            default: getChildren().addAll(resetGame(), abandonGame(), newGame()); break;
        }
    }
}
