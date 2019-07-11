package settings.GUI.buttons;

import javafx.scene.control.Button;
import settings.GUI.panes.GameSelectionPane;
import settings.GUI.panes.TopBarPane;
import settings.user.UserManager;
import settings.user.user.UserScore;
import settings.user.user.UserSettings;

import static settings.GUI.panes.LoginPane.*;
import static settings.AppSettings.*;

public interface LoginButton {
    // TODO learn more about this::blabla
    // example from https://code.makery.ch/blog/javafx-8-event-handling-examples
    // btnSignUp.setOnAction(this::signUpButton);

    // TODO Do this for the hangman fields <3

    /**
     * Default login button, does a clicky-click to try and log in
     * with given credentials.
     * @return functional login button that will throw errors in LoginPane()
     */
    default Button login() {
        Button btnLogin = new Button("Log In");
        btnLogin.setDefaultButton(true);

        // button action
        btnLogin.setOnAction(e -> loginAction(
                tfUserName.getText(),
                tfUserPassword.getText(),
                cbRememberUser.isSelected()));

        return btnLogin;
    }

    default void loginAction(String username, String password, boolean rememberUser) {
        // Check if user exists in the database
        if (UserManager.findUserName(username)) {
            // Check password match
            if (UserManager.matchPassword(username, password)) {
                // Set user as user
                user = UserManager.getUserProfile(username, password);
                user.getUserSettings().setRememberUser(rememberUser);

                // Continue to game selection screen
                pane.setTop(new TopBarPane());
                pane.setCenter(new GameSelectionPane());
            } else {
                // TODO how many password attempts?
                loginError("PasswordNoMatch");
            }
        } else {
            loginError("UserDoesNotExist");
        }

    }
}
