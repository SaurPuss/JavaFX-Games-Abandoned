package settings.GUI.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface UserScoreButton {

    /**
     * Create a default Button to toggle the ScoreboardPane
     * @return Button
     */
    default Button userScoreButton() {
        Button button = new Button();
        button.setGraphic(new ImageView(new Image("assets/icons/icons-scores_24.png")));

        button.setOnAction(e -> {
            // do stuff
        });

        return button;
    }
}
