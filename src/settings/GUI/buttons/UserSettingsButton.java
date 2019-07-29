package settings.GUI.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import settings.GUI.panes.SaveAnonymousPane;
import settings.GUI.panes.UserSettingsPane;
import settings.AppSettings;

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
            if (!AppSettings.user.isDefaultUser())
                AppSettings.pane.setCenter(new UserSettingsPane());
            else
                AppSettings.pane.setCenter(new SaveAnonymousPane());
        });

        return button;
    }
}
