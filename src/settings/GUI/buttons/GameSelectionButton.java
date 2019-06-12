package settings.GUI.buttons;

import game.hangman.Hangman;
import game.minesweeper.MinesweeperGame;
import javafx.scene.control.Button;
import settings.Session;

public interface GameSelectionButton {

    default Button gameSelectionButton(String selection) {
        Button btnSelect = new Button("Play!");

        btnSelect.setOnAction(e -> {
            switch (selection) {
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
