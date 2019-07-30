package settings.GUI.panes;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import settings.GUI.buttons.CreateUserButton;

import static org.controlsfx.control.textfield.TextFields.*;

public class SignUpPane extends VBox implements CreateUserButton {
    // Settings
    public static TextField tfName = createClearableTextField();
    public static PasswordField tfPassword = createClearablePasswordField();
    private static PasswordField tfConfirmPass = createClearablePasswordField();
    private CheckBox cUser = new CheckBox();
    private CheckBox cPass = new CheckBox();

    // Labels
    private Label lName = new Label("Username: ", tfName);
    private Label lPassword = new Label("Password: ", tfPassword);
    private Label lConfirm = new Label("Confirm Password: ", tfConfirmPass);
    private Label lUser = new Label("Remember User", cUser);
    private Label lPass = new Label("Rememeber Password", cPass);


    SignUpPane() {
        // no
    }

    public SignUpPane(String name, String password, boolean rememberUser) {
        // Use pre-filled settings from Login Pane





    }

    public SignUpPane(String name, String password, boolean rememberUser,
                      boolean rememberPassword) {
        // Use pre-filled settings from  User Settings Sign Up





    }

    // TODO Validate username
    // TODO Save user in db and current User
    // TODO Set this user to AppSettings


    public static boolean samePassword() {
        return tfPassword.getText().equals(tfConfirmPass.getText());
    }
}
