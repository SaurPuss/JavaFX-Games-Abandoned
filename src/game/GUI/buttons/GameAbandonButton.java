package game.GUI.buttons;

import javafx.scene.control.Button;
import settings.AppSettings;
import settings.GUI.panes.GameSelectionPane;

public interface GameAbandonButton {

    default Button abandonGame() {
        Button button = new Button("Abandon");

        button.setOnAction(e -> {
            AppSettings.pane.setCenter(new GameSelectionPane());
            System.out.println("ABANDON GAME BUTTON: Please add score integration, possibly in override");
            AppSettings.pane.setBottom(null);
            AppSettings.activeGame = false;
        });

        return button;
    }
}
