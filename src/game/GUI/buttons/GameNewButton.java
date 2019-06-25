package game.GUI.buttons;

import javafx.scene.control.Button;
import org.apache.commons.lang3.StringUtils;
import settings.AppSettings;

public interface GameNewButton {

    /**
     * Create a button that will set a new instance of the current game
     * as the main view pane. This will not influence the user scores
     * as that is handled by the game. Should only be used if there is
     * no current game in progress.
     * @return Button that hooks into AppSettings Game Selection
     */
    default Button newGame() {
        Button button = new Button("New Game");

        button.setOnAction(e -> {
            String selection = StringUtils.substringAfterLast(AppSettings.game.getClass().getName(), ".");
            AppSettings.gameSelection(selection);
        });

        return button;
    }
}
