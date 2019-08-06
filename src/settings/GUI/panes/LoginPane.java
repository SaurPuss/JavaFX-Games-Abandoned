package settings.GUI.panes;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import settings.GUI.buttons.*;
import settings.user.user.User;

import static org.controlsfx.control.textfield.TextFields.*;
import static settings.AppSettings.*;

public class LoginPane extends VBox implements LoginButton, SignUpButton, ContinueAnonymousButton {
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
        welcome = new Text("Welcome " + user.getName());
        getChildren().add(welcome);
        welcome.setFont(Font.font(18));
        cbRememberUser.setSelected(user.getUserSettings().isRememberUser());
        setSpacing(5);
        setAlignment(Pos.CENTER);

        buttons.getChildren().addAll(login(), signUp());
        getChildren().addAll(fields, buttons, continueAnonymous());
    }

    /**
     * Constructor in case current User is not default on startup
     * @param user non default user saved in currentUser.dat
     */
    public LoginPane(User user) {
        welcome = new Text("Welcome " + user.getName());
        welcome.setFont(Font.font(18));
        cbRememberUser.setSelected(user.getUserSettings().isRememberUser());
        tfUserName.setText(user.getName());
        buttons.getChildren().addAll(login(), signUp());
        setSpacing(5);
        setAlignment(Pos.CENTER);
        getChildren().addAll(welcome, fields, buttons);
    }

    /**
     * Add error message to sign up fields for better UX
     * @param err string for switch case
     */
    public static void loginError(String err) {
        fields.getChildren().removeAll(errUser, errPassword);
        errUser.setFill(Color.RED);
        errUser.setFont(Font.font(10));
        errPassword.setFill(Color.RED);
        errPassword.setFont(Font.font(10));

        switch(err) {
            // Login mismatch
            case "UserDoesNotExist": fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* Username does not exist.");
                tfUserName.selectAll(); break;
            case "PasswordNoMatch": fields.add(errPassword, 0, 3, 3, 1);
                errPassword.setText("* Password does not match.");
                tfUserPassword.selectAll();
                System.out.println("LOGIN PANE: Password does not match username."); break;
            default: fields.add(errPassword, 0, 3, 3, 1);
                errPassword.setText("* Username or Password can't be found.");
        }
    }
}
