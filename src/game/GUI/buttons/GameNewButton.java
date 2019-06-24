package game.GUI.buttons;

import javafx.scene.control.Button;

public interface GameNewButton {

    default Button newGame() {
        Button button = new Button("New Game");

        button.setOnAction(e -> System.out.println("NEW GAME BUTTON: Please override in implementing class."));

        return button;
    }

    // TODO create a method that catches the current game and returns a new instance of it to be implemented bu the newGame method
}
