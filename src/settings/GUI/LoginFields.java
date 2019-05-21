package settings.GUI;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginFields extends VBox implements Loginterface {
    private TextField userName = new TextField();
    private TextField userPassword = new TextField();

    public LoginFields() {
        // Set up the Labels to make it pretty
        Label lblUserName = new Label("Username", userName);
        Label lblUserPassword = new Label("Password", userPassword);

        lblUserName.setContentDisplay(ContentDisplay.BOTTOM);
        lblUserPassword.setContentDisplay(ContentDisplay.BOTTOM);

        // Add the buttons to make it functional
        Button btnLogin = new Button("Log in");
        Button btnSignUp = new Button("Sign Up");

        // TODO learn more about this::blabla
        // example from https://code.makery.ch/blog/javafx-8-event-handling-examples
        // btnSignUp.setOnAction(this::signUp);

        // TODO Do this for the hangman fields <3
        // Get default methods from Loginterface
        btnLogin.setOnAction(e -> login(userName.getText(), userPassword.getText()));
        btnSignUp.setOnAction(e -> signUp(userName.getText(), userPassword.getText()));

    }
}
