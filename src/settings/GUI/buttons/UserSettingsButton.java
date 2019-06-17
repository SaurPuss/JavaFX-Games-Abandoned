package settings.GUI.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import settings.GUI.panes.UserSettingsPane;
import settings.Session;

public interface UserSettingsButton {

    /**
     * Create a default Button to toggle the UserSettingsPane
     * @return Button
     */
    default Button userSettingsButton() {
        // TODO if default user defer to sign up screen
        Button button = new Button();
        Image image = new Image(getClass().getResourceAsStream("/assets/icons/icon-settings.png"));
        button.setGraphic(new ImageView(image));

        // TODO make this only a visual thing, maybe on top? So that you can flip back to an existing game
        button.setOnAction(e -> Session.pane.setCenter(new UserSettingsPane()));

        return button;
    }
}
