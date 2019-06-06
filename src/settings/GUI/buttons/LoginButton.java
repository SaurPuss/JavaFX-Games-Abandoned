package settings.GUI.buttons;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import settings.GUI.panes.GameSelectionPane;
import settings.GUI.panes.LoginPane;
import settings.GUI.panes.TopBarPane;
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

        // TODO move into its own stuff, because this is lazy
        LoginPane.tfUserPassword.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER)
                loginAction(LoginPane.tfUserName.getText(),
                        LoginPane.tfUserPassword.getText(),
                        LoginPane.cbRememberUser.isSelected());
        });
        // button action
        btnLogin.setOnAction(e -> loginAction(
                LoginPane.tfUserName.getText(),
                LoginPane.tfUserPassword.getText(),
                LoginPane.cbRememberUser.isSelected()));

        return btnLogin;
    }

    default void loginAction(String username, String password, boolean rememberUser) {
        // Check if user exists in the database
        if (UserManager.findUserName(username)) {
            // Check password match
            if (UserManager.matchPassword(username, password)) {
                // Set user as user
                Session.user = UserManager.getUserProfile(username, password);
                Session.user.setRememberUser(rememberUser);

                Session.printSessionUser();
//                UserManager.saveExistingUser(UserManager.getUserProfile(username, password));
                // Continue to game selection screen
                Session.pane.setTop(new TopBarPane());
                Session.pane.setCenter(new GameSelectionPane());
            } else {
                // TODO how many password attempts?
                LoginPane.loginError("PasswordNoMatch");
            }
        } else {
            LoginPane.loginError("UserDoesNotExist");
        }

    }
}
