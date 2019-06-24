package game.minesweeper;

import game.Game;
import settings.Session;

public class Minesweeper extends Game {

    public Minesweeper() {
        // Let the program know a game is active
        Session.activeGame = true;


    }
}
