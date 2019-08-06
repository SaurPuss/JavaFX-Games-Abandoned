package settings.GUI.buttons;

import javafx.scene.control.Button;

public interface LogOutButton {


    default Button logOutButton() {
        Button button = new Button("Log Out");

        button.setOnAction(e -> {
            // TODO make stuff happen here
            System.out.println("LOG OUT BUTTON: Basically save scores, reset user and plop in the login pane?");
        });

        return button;
    }
}
