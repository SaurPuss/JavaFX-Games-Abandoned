package game.GUI.buttons;

import javafx.scene.control.Button;
import org.apache.commons.lang3.StringUtils;
import settings.AppSettings;

public interface GameResetButton {

    /**
     * Return a new instance of the current game and set it in the main
     * view pane. It also gives a score penalty in the User Scores, because
     * it interrupts the current game.
     * @return Button that calls the AppSettings Game Selection and activates Scores
     */
    default Button resetGame() {
        Button button = new Button("Reset Game");

        button.setOnAction(e -> {
            String selection = StringUtils.substringAfterLast(AppSettings.game.getClass().getName(), ".");
            AppSettings.gameSelection(selection);
            // TODO Score stuff
            // TODO Warning about score stuff
            // TODO Score stuff dependent on difficulty level
        });

        return button;
    }
}
