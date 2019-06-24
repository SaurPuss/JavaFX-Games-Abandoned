package game.GUI.buttons;

import javafx.scene.control.Button;
import settings.GUI.panes.GameSelectionPane;
import settings.Session;

public interface GameAbandonButton {

    default Button abandonGame() {
        Button button = new Button("Abandon");

        button.setOnAction(e -> {
            Session.pane.setCenter(new GameSelectionPane());
            System.out.println("ABANDON GAME BUTTON: Please add score integration, possibly in override");
            Session.pane.setBottom(null);
        });

        return button;
    }
}
