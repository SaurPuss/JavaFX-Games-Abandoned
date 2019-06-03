package settings.GUI.buttons;

import javafx.scene.control.Button;
import settings.GUI.panes.GameSelectionPane;
import settings.GUI.panes.LoginPane;
import settings.Session;
import settings.user.UserManager;

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

        // Attach event to the button
        btnLogin.setOnAction(e -> {
            // Check if user exists in the database
            if (UserManager.findUserName(LoginPane.tfUserName.getText())) {
                // Check password match
                if (UserManager.matchPassword(LoginPane.tfUserName.getText(), LoginPane.tfUserPassword.getText())) {
                    // Set user as user
                    Session.user = UserManager.getUserProfile(LoginPane.tfUserName.getText(),
                            LoginPane.tfUserPassword.getText());
                    // Continue to game selection screen
                    Session.pane.setCenter(new GameSelectionPane());
                } else {
                    // TODO how many password attempts?
                    LoginPane.loginError("PasswordNoMatch");
                }
            } else {
                LoginPane.loginError("UserDoesNotExist");
            }
        });

        return btnLogin;
    }
}
