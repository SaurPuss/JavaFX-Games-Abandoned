package settings.GUI.buttons;

import javafx.scene.control.Button;
import settings.GUI.panes.*;
import settings.user.UserManager;

import static settings.AppSettings.*;

public interface SignUpButton {
    /**
     * Default Sign Up Button that fetches information from
     * LoginPane or UserSettingsPane to forward the info to
     * CreateUserPane for formal sign up.
     */
    default Button signUp() {
        Button btnSignUp = new Button("Sign Up");

        btnSignUp.setOnAction(e -> {
            // TODO switch case instead?
            if (pane.getCenter().getClass().isInstance(LoginPane.class)) {
                // LOGIN PANE
                pane.setCenter(new SignUpPane(
                        LoginPane.tfUserName.getText(),
                        LoginPane.tfUserPassword.getText(),
                        LoginPane.cbRememberUser.isSelected()));
            } else if (pane.getCenter().getClass().isInstance(UserSettingsPane.class)) {
                // USER SETTINGS PANE
                pane.setCenter(new SignUpPane(
                        UserSettingsPane.tfName.getText(),
                        UserSettingsPane.tfPassword.getText(),
                        UserSettingsPane.toggleUser.isSelected(),
                        UserSettingsPane.togglePassword.isSelected()));
            } else {
                // SIGN UP PANE
                if (UserManager.findUserName(SignUpPane.tfName.getText())) {
                    // user name is taken

                } else if (SignUpPane.samePassword()) {
                    // password does not match in the fields

                } else {
                    // TODO set user up in current user
                    // TODO save user info from pane to DB
                    pane.setCenter(new GameSelectionPane());
                }
            }
        });

        return btnSignUp;
    }
}
