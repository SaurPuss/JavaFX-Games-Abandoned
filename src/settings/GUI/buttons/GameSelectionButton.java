package settings.GUI.buttons;

import game.hangman.Hangman;
import game.minesweeper.Minesweeper;
import javafx.scene.control.Button;
import settings.Session;

public interface GameSelectionButton {

    default Button gameSelectionButton(String selection) {
        Button btnSelect = new Button("Play!");

        btnSelect.setOnAction(e -> {
            switch (selection) {
                case "Hangman":
                    Session.game = new Hangman(); break;
                case "Minesweeper":
                    Session.game = new Minesweeper(); break;
                default:
                    System.out.println("GAME SELECTION BUTTON: Default case. Nothing happens.");
                    Session.game = new Hangman(); // default hangman to make sure that there is *something*
            }

            Session.pane.setCenter(Session.game);
        });

        return btnSelect;
    }
}
