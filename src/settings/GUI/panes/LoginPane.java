package settings.GUI.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import settings.GUI.buttons.LoginButton;
import settings.GUI.buttons.SignUpButton;
import settings.Session;
import settings.user.User;

public class LoginPane extends VBox implements LoginButton, SignUpButton {
    private static Text welcome;
    private static Text chooseAction = new Text(
            "Unless you want to remain anonymous " +
            "you should log in or sign up.");
    private static Text errUser = new Text("");
    private static Text errPassword = new Text("");
    private static TextField tfUserName = new TextField();
    private static TextField tfUserPassword = new TextField();
    private static Text tUserName = new Text("Username:  ");
    private static Text tUserPassword = new Text("Password:  ");
    private static Text tRemember = new Text(" Remember me");
    private static CheckBox cbRememberUser = new CheckBox();

    private static GridPane fields = new GridPane();
    private static HBox buttons = new HBox(5);

    static { // Set this up for all LoginPane() objects
        chooseAction.setWrappingWidth(250);
        chooseAction.setTextAlignment(TextAlignment.CENTER);

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
        welcome = new Text("Welcome " + Session.user.getUserName());
        welcome.setFont(Font.font(18));
        cbRememberUser.setSelected(false);

        buttons.getChildren().addAll(
                login(tfUserName.getText(), tfUserPassword.getText()),
                signUp(tfUserName.getText(), tfUserPassword.getText()));

        setSpacing(5);
        setAlignment(Pos.CENTER);
        getChildren().addAll(welcome, chooseAction, fields, buttons);
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

        buttons.getChildren().addAll(
                login(tfUserName.getText(), tfUserPassword.getText()),
                signUp(tfUserName.getText(), tfUserPassword.getText()));

        setSpacing(5);
        setAlignment(Pos.CENTER);
        getChildren().addAll(welcome, chooseAction, fields, buttons);
    }

    /**
     * Add error message to sign up fields for better UX
     * @param err integer for switch case
     */
    public static void loginError(int err) {
        // Clear error messages from GridPane in case of multiple invocations
        fields.getChildren().removeAll(errUser, errPassword);

        errUser.setFill(Color.RED);
        errUser.setFont(Font.font(10));
        errPassword.setFill(Color.RED);
        errPassword.setFont(Font.font(10));

        switch(err) {
            case 1: fields.add(errUser, 0, 1, 3, 1);
                errUser.setText("* Username does not exist."); break;
            case 2: fields.add(errPassword, 0, 3, 3, 1);
                errPassword.setText("* Password does not match."); break;
            default: fields.add(errPassword, 0, 3, 3, 1);
                errPassword.setText("* Username or Password can't be found.");
        }
    }
}
