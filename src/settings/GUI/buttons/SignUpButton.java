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
            // TODO is there a way to shorten the package bits?
            if (pane.getCenter().getClass().equals(settings.GUI.panes.LoginPane.class)) {
                // LOGIN PANE
                System.out.println("SIGN UP BUTTON: Swapping from LoginPane to SignUpPane");
                pane.setCenter(new SignUpPane(
                        LoginPane.tfUserName.getText(),
                        LoginPane.tfUserPassword.getText(),
                        LoginPane.cbRememberUser.isSelected()));
            } else if (pane.getCenter().getClass().equals(settings.GUI.panes.UserSettingsPane.class)) {
                // USER SETTINGS PANE
                System.out.println("SIGN UP BUTTON: Swapping from UserSettingsPane to SignUpPane");
                pane.setCenter(new SignUpPane(
                        UserSettingsPane.tfName.getText(),
                        UserSettingsPane.tfPassword.getText(),
                        UserSettingsPane.toggleUser.isSelected(),
                        UserSettingsPane.togglePassword.isSelected()));
            } else {
                // SIGN UP PANE
                System.out.println(pane.getCenter().getClass());
                System.out.println("SIGN UP BUTTON: Saving from SignUpPane, probably");
                if (UserManager.findUserName(SignUpPane.tfName.getText())) {
                    SignUpPane.signUpError("UsernameAlreadyExists");
                } else if (!SignUpPane.samePassword()) {
                    SignUpPane.signUpError("PasswordNoMatch");
                } else {
                    // TODO if this is a anonymous user save, and a game is active, will the game remain?
                    // Save user to database and return it to the Session user
                    user = UserManager.saveUser(
                            SignUpPane.tfName.getText(),
                            SignUpPane.tfPassword.getText(),
                            SignUpPane.cUser.isSelected(),
                            SignUpPane.cPass.isSelected());
                    // Set user as current
                    UserManager.saveCurrentUser(user);
                    // Continue to game selection screen
                    pane.setCenter(new GameSelectionPane());
                    pane.setTop(new TopBarPane());
                }
            }
        });

        return btnSignUp;
    }
}
