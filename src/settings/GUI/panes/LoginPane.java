package settings.GUI.panes;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import settings.GUI.buttons.LoginButton;
import settings.GUI.buttons.SignUpButton;
import settings.user.User;

import static org.controlsfx.control.textfield.TextFields.*;
import static settings.Session.*;

public class LoginPane extends VBox implements LoginButton, SignUpButton {
    private static Text welcome;
    private static Text errUser = new Text("");
    private static Text errPassword = new Text("");
    public static TextField tfUserName = createClearableTextField();
    public static PasswordField tfUserPassword = createClearablePasswordField();
    private static Text tUserName = new Text("Username:  ");
    private static Text tUserPassword = new Text("Password:  ");
    private static Text tRemember = new Text(" Remember me");
    public static CheckBox cbRememberUser = new CheckBox();

    private static GridPane fields = new GridPane();
    private static HBox buttons = new HBox(0);

    static { // Set this up for all LoginPane() objects

        fields.add(tUserName, 0, 0, 2, 1);
        fields.add(tfUserName, 2, 0, 2, 1);
        fields.add(tUserPassword, 0, 2, 2, 1);
        fields.add(tfUserPassword, 2,2, 2, 1);
        fields.add(cbRememberUser, 0, 4,1,1);
        fields.add(tRemember, 1, 4, 2, 1);

        fields.setPadding(new Insets(20, 0, 10, 0));
        fields.setAlignment(Pos.CENTER);
        fields.setVgap(5);
        buttons.setAlignment(Pos.CENTER);
    }

    /**
     * Default constructor for login/signup form
     */
    public LoginPane() {
        printSessionUser();
        getChildren().add(welcome);
        welcome = new Text("Welcome " + user.getUserName());
        if (User.isDefaultUser()) {
            Text chooseAction = new Text("Unless you want to remain anonymous" +
                    "\nyou should log in or sign up.");
            chooseAction.setTextAlignment(TextAlignment.CENTER);
            // TODO add continue as default button
        }

        welcome.setFont(Font.font(18));
        cbRememberUser.setSelected(user.isRememberUser());

        buttons.getChildren().addAll(login(), signUp());

        setSpacing(5);
        setAlignment(Pos.CENTER);
        getChildren().addAll(fields, buttons);

//        textFieldLogin();
    }

    /**
     * Constructor in case current User is not default on startup
     * @param user non default user saved in currentUser.dat
     */
    public LoginPane(User user) {
        welcome = new Text("Welcome " + user.getUserName());
        welcome.setFont(Font.font(18));
        cbRememberUser.setSelected(user.isRememberUser());
        tfUserName.setText(user.getUserName());

        buttons.getChildren().addAll(login(), signUp());

        setSpacing(5);
        setAlignment(Pos.CENTER);
        getChildren().addAll(welcome, fields, buttons);

//        textFieldLogin();
    }

    /**
     * Add error message to sign up fields for better UX
     * @param err string for switch case
     */
    public static void loginError(String err) {
        // Clear error messages from GridPane in case of multiple invocations
        fields.getChildren().removeAll(errUser, errPassword);

        errUser.setFill(Color.RED);
        errUser.setFont(Font.font(10));
        errPassword.setFill(Color.RED);
        errPassword.setFont(Font.font(10));

        switch(err) {
            // Login mismatch
            case "UserDoesNotExist": fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* Username does not exist.");
//                System.out.println("LOGIN PANE: User does not exist in database.");
                break;
            case "PasswordNoMatch": fields.add(errPassword, 0, 3, 3, 1);
                errPassword.setText("* Password does not match.");
                System.out.println("LOGIN PANE: Password does not match username."); break;
            // Username errors
            case "UsernameEmpty": fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* Username is missing.");
                System.out.println("LOGIN PANE: Username Textfield is empty."); break;
            case "DefaultUsername": fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* You're using a default Username. \nPlease choose something else.");
                System.out.println("LOGIN PANE: Username is default."); break;
            case "UsernameTooShort": fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* A Username must have more than 6 characters. \nPlease choose something else.");
                System.out.println("LOGIN PANE: Username is too short."); break;
            case "UsernameAlreadyExists": fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* This username is already taken. \nPlease choose something else.");
                System.out.println("LOGIN PANE: Username already exists."); break;
            case "ProtectedUsername": fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* This username is already taken. \nPlease choose something else.");
                System.out.println("LOGIN PANE: Protected Username."); break;
            // Password errors
            case "PasswordEmpty": fields.add(errPassword, 0, 3, 3, 1);
                errPassword.setText("* Please create a password.");
                System.out.println("LOGIN PANE: Password Textfield is empty."); break;
            case "PasswordTooShort": fields.add(errPassword, 0, 3, 3, 1);
                errPassword.setText("* A Password must have more than 6 characters. \nPlease choose something else.");
                System.out.println("LOGIN PANE: Password is too short."); break;
            default: fields.add(errPassword, 0, 3, 3, 1);
                errPassword.setText("* Username or Password can't be found.");
        }
    }

    /**
     * Helper method that will try to log in a user that hits enter while
     * in the password TextField.
     */
    private void textFieldLogin() {
        tfUserPassword.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER)
                loginAction(LoginPane.tfUserName.getText(),
                        LoginPane.tfUserPassword.getText(),
                        LoginPane.cbRememberUser.isSelected());
        });
    }
}
