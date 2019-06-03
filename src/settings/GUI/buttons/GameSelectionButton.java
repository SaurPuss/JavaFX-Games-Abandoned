package settings.GUI.buttons;

import game.hangman.HangmanGame;
import game.hangman.Hangman;
import game.minesweeper.MinesweeperGame;
import javafx.scene.control.Button;
import settings.Session;

public interface GameSelectionButton {

    default Button gameSelectionButton(String game) {
        Button btnSelect = new Button("Play!");

        btnSelect.setOnAction(e -> {
            switch (game) {
                case "Hangman":
                    Session.pane.setCenter(new Hangman()); break;
                case "Minesweeper":
                    Session.pane.setCenter(new MinesweeperGame()); break;
                default:
                    System.out.println("GAME SELECTION BUTTON: Default case. Nothing happens.");
            }
        });

        return btnSelect;
    }
}
