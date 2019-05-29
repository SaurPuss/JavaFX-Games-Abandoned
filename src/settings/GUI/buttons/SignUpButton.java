package settings.GUI.buttons;

import javafx.scene.control.Button;
import settings.GUI.panes.LoginPane;
import settings.user.User;
import settings.user.UserManager;

public interface SignUpButton {
    /**
     * Default Sign Up Button that fetches information from
     * LoginPane to handle the signUpAction method.
     */
    default Button signUp() {
        Button btnSignUp = new Button("Sign Up");

        btnSignUp.setOnAction(e -> {
            // TODO Add password confirm field
            signUpAction(LoginPane.tfUserName.getText(), LoginPane.tfUserPassword.getText());
        });

        return btnSignUp;
    }

    /**
     * Convenience method that insures new information is
     * pulled from the LoginPane Textfields every time the
     * button is clicked.
     * @param username String to verify for validity
     * @param password String to verify for validity
     */
    default void signUpAction(String username, String password) {
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
            System.out.println("SIGN UP: Please make sure your password is at least 6 characters long");
        }
        // Invoke
        else {
            // TODO update saveNewUser method & also the updateExistingUser method
            UserManager.saveNewUser(username, password);
            System.out.println("SIGN UP BUTTON: saving user");
            // Set user as Session user

            // If remember me is clicked set user to currentUser.dat

            // Continue to game selection pane
        }
    }
}
