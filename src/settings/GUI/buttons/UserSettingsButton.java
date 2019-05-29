package settings.GUI.buttons;

import javafx.scene.control.Button;

public interface UserSettingsButton {

    /**
     * Create a default Button to toggle the UserSettingsPane
     * @return Button
     */
    default Button userSettingsButton() {
        // TODO if default user defer to sign up screen

        return new Button();
    }
}
