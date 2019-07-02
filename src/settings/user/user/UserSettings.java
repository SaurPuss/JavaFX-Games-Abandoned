package settings.user.user;

import settings.user.settings.GameDifficulty;

import java.io.*;

import static settings.AppSettings.*;

public class UserSettings implements Serializable {
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    /* Object Data Fields */
    private boolean rememberPassword;
    private boolean rememberUser;
    private GameDifficulty gameDifficulty;

    /* Constructors */
    UserSettings() {
        rememberPassword = false;
        rememberUser = false;
        gameDifficulty = GameDifficulty.NORMAL;
    }

    UserSettings(boolean rememberUser) {
        rememberPassword = false;
        this.rememberUser = rememberUser;
        gameDifficulty = GameDifficulty.NORMAL;
    }

    /* Getters and Setters */
    public boolean isRememberPassword() { return rememberPassword; }
    public void setRememberPassword(boolean rememberPassword) { this.rememberPassword = rememberPassword; }
    public boolean isRememberUser() { return rememberUser; }
    public void setRememberUser(boolean rememberUser) { this.rememberUser = rememberUser; }
    public GameDifficulty getGameDifficulty() { return gameDifficulty; }
    public void setGameDifficulty(GameDifficulty gameDifficulty) { this.gameDifficulty = gameDifficulty; }

    /**
     * Implement Serializable Interface methods
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("USER SETTINGS: no Object data");
    }
}
