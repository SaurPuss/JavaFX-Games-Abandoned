package settings.GUI.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import settings.GUI.buttons.UserLoginButtons;
import settings.user.User;

public class LoginPane extends VBox implements UserLoginButtons {
    private TextField tfUserName = new TextField();
    private TextField tfUserPassword = new TextField();
    private CheckBox cbRememberUser = new CheckBox();

    private LoginPane(){
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);

        cbRememberUser.setSelected(false);
    }

    public LoginPane(User user) {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);

        tfUserName.setText(user.getUserName());
        cbRememberUser.setSelected(user.isRememberUser());






        //Upon login save remember user bool


    }

/*
    public LoginPane(User user) {
        // A little bit of styling


        // Introduction or something
        VBox splashPane = new VBox(10);
        Text splashTitle = new Text("Welcome " + user.getUserName() + "! \nLet's play a game!");

        VBox continuePane = new VBox();
        Button btnContinue = new Button("Continue as " + user.getUserName());

        splashTitle.setTextAlignment(TextAlignment.CENTER);
        splashTitle.setFont(Font.font(14));
        splashPane.setAlignment(Pos.CENTER);
        splashPane.setPadding(new Insets(10,10,30, 10));
        splashPane.getChildren().addAll(splashTitle);

        continuePane.setAlignment(Pos.CENTER);
        continuePane.setPadding(new Insets(30, 0, 0, 0));
        continuePane.getChildren().add(btnContinue);

        // Set up the Labels to make it pretty
        Label lblUserName = new Label("Username", tfUserName);
        Label lblUserPassword = new Label("Password", tfUserPassword);

        lblUserName.setContentDisplay(ContentDisplay.RIGHT);
        lblUserPassword.setContentDisplay(ContentDisplay.RIGHT);

        // Add the buttons to make it functional
        HBox buttons = new HBox(10);
        Button btnLogin = new Button("Log in");
        Button btnSignUp = new Button("Sign Up");

        btnLogin.setMinWidth(115);
        btnSignUp.setMinWidth(115);
        buttons.getChildren().addAll(btnLogin, btnSignUp);
        buttons.setAlignment(Pos.CENTER);

        HBox rememberPane = new HBox();
        Label lblRemeberUser = new Label("Remember me", cbRememberUser);
        lblRemeberUser.setContentDisplay(ContentDisplay.LEFT);
        rememberPane.getChildren().addAll(cbRememberUser, lblRemeberUser);

        // TODO learn more about this::blabla
        // example from https://code.makery.ch/blog/javafx-8-event-handling-examples
        // btnSignUp.setOnAction(this::signUpButton);

        // TODO Do this for the hangman fields <3
        // Get default methods from UserLoginButtons
        btnLogin.setOnAction(e -> {
            if (cbRememberUser.isSelected()) {
                UserManager.saveCurrentUser(new User(tfUserName.getText(), tfUserPassword.getText()));
                loginButton(tfUserName.getText(), tfUserPassword.getText());
                // continue to next screen
            } else {
                loginButton(tfUserName.getText(), tfUserPassword.getText());
                UserManager.deleteCurrentUser(); // Make sure the current user is saved to random default
                // continue to next screen
            }

        });
        btnSignUp.setOnAction(e -> signUpButton(tfUserName.getText(), tfUserPassword.getText()));

        getChildren().addAll(splashPane, lblUserName, tfUserName, lblUserPassword, tfUserPassword,
                rememberPane, buttons, continuePane);
    }
*/
}
