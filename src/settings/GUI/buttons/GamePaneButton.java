package settings.GUI.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import settings.Session;

public interface GamePaneButton {

    default Button gamePaneButton() {
        Button button = new Button();
        button.setGraphic(new ImageView(new Image("assets/icons/icons-game-controller_24.png")));

        button.setOnAction(e -> {
            // TODO Make it so that it goes to the game selection pane if game is current pane
            Session.pane.setCenter(Session.game);
        });

        return button;
    }
}
