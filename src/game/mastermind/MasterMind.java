package game.mastermind;

import game.Game;
import javafx.scene.control.Button;
import settings.AppSettings;
import settings.GUI.panes.GameSelectionPane;

public class MasterMind extends Game {

    public MasterMind() {
        // Let the program know a game is active
        AppSettings.activeGame = true;

        Button button = new Button("Go Back");
        button.setOnAction(e -> AppSettings.pane.setCenter(new GameSelectionPane()));
        getChildren().add(button);

    }
}
