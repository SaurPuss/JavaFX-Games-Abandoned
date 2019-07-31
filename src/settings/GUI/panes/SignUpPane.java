package settings.GUI.panes;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import settings.GUI.buttons.CreateUserButton;

import static org.controlsfx.control.textfield.TextFields.*;

public class SignUpPane extends VBox implements CreateUserButton {
    // Settings
    public static TextField tfName = createClearableTextField();
    public static PasswordField tfPassword = createClearablePasswordField();
    private static PasswordField tfConfirmPass = createClearablePasswordField();
    public static CheckBox cUser = new CheckBox();
    public static CheckBox cPass = new CheckBox();

    // Labels
    private static Label lName = new Label("Username: ");
    private static Label lPassword = new Label("Password: ");
    private static Label lConfirm = new Label("Confirm Password: ");
    private static Label lUser = new Label("Remember Username", cUser);
    private static Label lPass = new Label("Rememeber Password", cPass);

    // Fields
    private static GridPane fields = new GridPane();
    private static Text errUser = new Text();
    private static Text errPassword = new Text();

    // Let's set up the fields in the gridpane
    static {
        fields.add(lName, 0, 0, 3, 1);
        fields.add(tfName, 3, 0, 8, 1);
        fields.add(lPassword, 0, 2, 3, 1);
        fields.add(tfPassword, 3,2, 8, 1);
        fields.add(lConfirm, 0, 3, 3, 1);
        fields.add(tfConfirmPass, 3, 3, 8, 1);

        fields.add(cUser, 0, 7,1,1);
        fields.add(lUser, 1, 7, 4, 1);
        fields.add(cPass, 0,8, 1,1);
        fields.add(lPass, 1, 8, 4, 1);

        fields.setPadding(new Insets(20, 0, 10, 0));
        fields.setAlignment(Pos.CENTER);
        fields.setVgap(5);
    }

    SignUpPane() {
        getChildren().add(new Text("What are you even doing here?"));
    }

    public SignUpPane(String name, String password, boolean rememberUser) {
        // Use pre-filled settings from Login Pane
        fields.add(createUserButton(), 3, 12, 6, 1);
        getChildren().add(fields);
        tfName.setText(name);
        tfPassword.setText(password);
        cUser.setSelected(rememberUser);

        // check name availability <- can i do that dynamic in the field?
        // check password match <- show in field?
        // do sign up actions and move to the game selection pane

    }

    public SignUpPane(String name, String password, boolean rememberUser,
                      boolean rememberPassword) {
        // Use pre-filled settings from  User Settings Sign Up
        fields.add(createUserButton(), 3, 12, 6, 1);
        getChildren().add(fields);
        tfName.setText(name);
        tfPassword.setText(password);
        cUser.setSelected(rememberUser);
        cPass.setSelected(rememberPassword);


    }


    public static boolean samePassword() {
        return tfPassword.getText().equals(tfConfirmPass.getText());
    }

    /**
     * Add error message to sign up fields for better UX
     * @param err string for switch case
     */
    public static void signUpError(String err) {
        fields.getChildren().removeAll(errUser, errPassword);
        errUser.setFill(Color.RED);
        errUser.setFont(Font.font(10));
        errPassword.setFill(Color.RED);
        errPassword.setFont(Font.font(10));

        switch(err) {
            // Login mismatch
            case "UserDoesNotExist": fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* Username does not exist.");
                tfName.selectAll();
                break;
            case "PasswordNoMatch": fields.add(errPassword, 0, 5, 3, 1);
                errPassword.setText("* Password does not match.");
                tfPassword.selectAll();
                System.out.println("SIGN UP PANE: Password does not match username."); break;
            // Username errors
            case "UsernameEmpty": fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* Username is missing.");
                System.out.println("SIGN UP PANE: Username Textfield is empty."); break;
            case "DefaultUsername": fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* You're using a default Username. \nPlease choose something else.");
                System.out.println("SIGN UP PANE: Username is default."); break;
            case "UsernameTooShort": fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* A Username must have more than 6 characters. \nPlease choose something else.");
                System.out.println("SIGN UP PANE: Username is too short."); break;
            case "UsernameAlreadyExists": fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* This username is already taken. \nPlease choose something else.");
                System.out.println("SIGN UP PANE: Username already exists."); break;
            case "ProtectedUsername": fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* This username is already taken. \nPlease choose something else.");
                System.out.println("SIGN UP PANE: Protected Username."); break;
            // Password errors
            case "PasswordEmpty": fields.add(errPassword, 0, 5, 3, 1);
                errPassword.setText("* Please create a password.");
                System.out.println("SIGN UP PANE: Password Textfield is empty."); break;
            case "PasswordTooShort": fields.add(errPassword, 0, 5, 3, 1);
                errPassword.setText("* A Password must have more than 6 characters. \nPlease choose something else.");
                System.out.println("SIGN UP PANE: Password is too short."); break;
            default: fields.add(errPassword, 0, 5, 3, 1);
                errPassword.setText("* Username or Password can't be found.");
        }
    }
}
