package settings.GUI.other;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


// TODO implement this in the sign up anonymous pane as well as the login pane
// TODO make the sign up and login buttons work with these fields
public interface SignUpTextFields {

    public static TextField tUsername() {




        return new TextField();
    }


    public static TextField tPassword() {



        return new TextField();
    }

    public static PasswordField pPassword() {


        return new PasswordField();
    }

}
