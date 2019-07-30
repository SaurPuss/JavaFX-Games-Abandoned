package settings.GUI.buttons;

import javafx.scene.control.Button;
import settings.AppSettings;
import settings.GUI.panes.*;

public interface SignUpButton {
    /**
     * Default Sign Up Button that fetches information from
     * LoginPane or UserSettingsPane to forward the info to
     * CreateUserPane for formal sign up.
     */
    default Button signUp() {
        Button btnSignUp = new Button("Sign Up");

        btnSignUp.setOnAction(e -> {
            // Check which Pane the info comes from
            if (AppSettings.pane.getCenter().getClass().isInstance(LoginPane.class))
                AppSettings.pane.setCenter(new SignUpPane(
                        LoginPane.tfUserName.getText(),
                        LoginPane.tfUserPassword.getText(),
                        LoginPane.cbRememberUser.isSelected()));
            else if (AppSettings.pane.getCenter().getClass().isInstance(UserSettingsPane.class))
                AppSettings.pane.setCenter(new SignUpPane(
                        UserSettingsPane.tfName.getText(),
                        UserSettingsPane.tfPassword.getText(),
                        UserSettingsPane.toggleUser.isSelected(),
                        UserSettingsPane.togglePassword.isSelected()));
        });

        return btnSignUp;
    }
}
