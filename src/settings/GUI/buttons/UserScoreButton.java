package settings.GUI.buttons;

import javafx.scene.control.Button;

public interface UserScoreButton {

    /**
     * Create a default Button to toggle the ScoreboardPane
     * @return Button
     */
    default Button userScoreButton() {


        return new Button();
    }
}
