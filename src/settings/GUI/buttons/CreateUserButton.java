package settings.GUI.buttons;

import javafx.scene.control.Button;
import settings.AppSettings;
import settings.GUI.panes.GameSelectionPane;
import settings.GUI.panes.LoginPane;
import settings.GUI.panes.SignUpPane;
import settings.GUI.panes.TopBarPane;
import settings.user.UserManager;
import settings.user.user.User;

import static settings.GUI.panes.LoginPane.cbRememberUser;

public interface CreateUserButton {



    default Button createNewUser() {
        Button button = new Button("Sign Up");

        button.setOnAction(e -> {
            if (SignUpPane.samePassword())
                signUpAction(SignUpPane.tfName.getText(), SignUpPane.tfPassword.getText());
        });

        return button;
    }


    /**
     * Convenience method that insures new information is
     * pulled from the LoginPane Textfields every time the
     * button is clicked.
     * @param username String to verify for validity
     * @param password String to verify for validity
     */
    default void signUpAction(String username, String password) {
        // TODO check LOGIN PANE FOR ERRORS AND VALIDATE THESE FIELDS HERE INSTEAD OF IN LOGIN
        // Errors cause by the username
        if ((username == null) || (username.equals(""))) {
            LoginPane.loginError("UsernameEmpty");
        } else if (User.isRandomName(username)) {
            LoginPane.loginError("DefaultUsername");
        } else if (username.length() < 6) {
            LoginPane.loginError("UsernameTooShort");
        } else if (UserManager.findUserName(username)) {
            LoginPane.loginError("UsernameAlreadyExists");
        }
        // Errors caused by the password
        else if ((password == null) || (password.equals(""))) {
            LoginPane.loginError("PasswordEmpty");
        } else if (password.length() < 6) {
            LoginPane.loginError("PasswordTooShort");
        }
        // Protected username
        else if ((username.toLowerCase().contains("anonymous")) || (username.toLowerCase().contains("saurpuss")) ||
                (username.toLowerCase().contains("battlecrow"))) {
            LoginPane.loginError("ProtectedUsername");
        }
        // Invoke
        else {
            // Save new user to database & set AppSettings.user & currentUser.dat
            User user = UserManager.saveUser(username, password, cbRememberUser.isSelected());
            if (user != null) {
                AppSettings.user = user;

                // Continue to game selection pane
                AppSettings.pane.setTop(new TopBarPane());
                AppSettings.pane.setCenter(new GameSelectionPane());
            }
        }
    }
}
