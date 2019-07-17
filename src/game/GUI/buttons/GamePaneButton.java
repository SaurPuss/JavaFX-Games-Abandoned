package game.GUI.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import settings.AppSettings;
import settings.GUI.panes.GameSelectionPane;

public interface GamePaneButton {

    default Button gamePaneButton() {
        Button button = new Button();
        button.setGraphic(new ImageView(new Image("assets/icons/icons-game-controller_24.png")));

        button.setOnAction(e -> {
            if (AppSettings.activeGame) {
                AppSettings.pane.setCenter(AppSettings.game);
            } else
                AppSettings.pane.setCenter(new GameSelectionPane());
        });

        return button;
    }
}
