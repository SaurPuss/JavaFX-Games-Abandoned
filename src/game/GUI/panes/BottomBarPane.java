package game.GUI.panes;

import game.GUI.buttons.GameAbandonButton;
import game.GUI.buttons.GameResetButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class BottomBarPane extends HBox implements GameAbandonButton, GameResetButton {

    public BottomBarPane() {
        setPadding(new Insets(10,0,10,0));
        setAlignment(Pos.CENTER);
        getChildren().addAll(resetGame(), abandonGame());
    }
}
