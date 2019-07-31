package settings.GUI.buttons;

import javafx.scene.control.Button;
import settings.AppSettings;
import settings.GUI.panes.*;
import settings.user.UserManager;
import settings.user.user.User;

public interface CreateUserButton {

    /**
     * This is where legit magic happens. Worship the
     * rabbit that lives in the drainpipe of your
     * basement bathroom sink.
     * @return button that will sign you up big time.
     */
    default Button createUserButton() {
        Button button = new Button("Sign Up");
        button.setDefaultButton(true);

        button.setOnAction(e -> signUpAction(
                SignUpPane.tfName.getText(),
                SignUpPane.tfPassword.getText()));

        return button;
    }


    /**
     * Convenience method that insures new information is
     * pulled from the SignUpPane TextFields every time
     * the button is clicked.
     * @param username String to verify for validity
     * @param password String to verify for validity
     */
    default void signUpAction(String username, String password) {
        // Errors cause by the username
        if ((username == null) || (username.equals(""))) {
            SignUpPane.signUpError("UsernameEmpty");
        } else if (User.isRandomName(username)) {
            SignUpPane.signUpError("DefaultUsername");
        } else if (username.length() < 6) {
            SignUpPane.signUpError("UsernameTooShort");
        } else if (UserManager.findUserName(username)) {
            SignUpPane.signUpError("UsernameAlreadyExists");
        }
        // Errors caused by the password
        else if ((password == null) || (password.equals(""))) {
            SignUpPane.signUpError("PasswordEmpty");
        } else if (password.length() < 6) {
            SignUpPane.signUpError("PasswordTooShort");
        } else if (!SignUpPane.samePassword()) {
            SignUpPane.signUpError("PasswordNoMatch");
        }
        // Protected username
        else if   ((username.toLowerCase().contains("anonymous"))
                || (username.toLowerCase().contains("saurpuss"))
                || (username.toLowerCase().contains("battlecrow"))) {
            SignUpPane.signUpError("ProtectedUsername");
        }
        // Invoke
        else {

            // TODO Save user in db and current User
            // TODO Set this user to AppSettings

            // Save new user to database & set AppSettings.user & currentUser.dat
            User user = UserManager.saveUser(username, password, SignUpPane.cUser.isSelected(), SignUpPane.cPass.isSelected());
            AppSettings.user = user;

            // Continue to game selection pane
            AppSettings.pane.setTop(new TopBarPane());
            AppSettings.pane.setCenter(new GameSelectionPane());
        }
    }
}
