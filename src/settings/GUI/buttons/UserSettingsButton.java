package settings.GUI.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import settings.GUI.panes.SignUpPane;
import settings.GUI.panes.UserSettingsPane;
import static settings.AppSettings.*;

public interface UserSettingsButton {

    /**
     * Create a default Button to toggle the UserSettingsPane
     * @return Button
     */
    default Button userSettingsButton() {
        Button button = new Button();
        button.setGraphic(new ImageView(new Image("assets/icons/icons-settings_24.png")));

        // TODO stop it from making infinite "new" panes
        // TODO if default user make this a sign up pane
        button.setOnAction(e -> {
            if (!user.isDefaultUser())
                pane.setCenter(new UserSettingsPane());
            else
                pane.setCenter(new SignUpPane("", "", false));
        });

        return button;
    }
}
