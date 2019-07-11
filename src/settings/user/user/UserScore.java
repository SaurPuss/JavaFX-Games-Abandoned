package settings.user.user;

import java.io.*;

import static settings.AppSettings.*;

/**
 * User saves scores in here
 */
public class UserScore implements Serializable {
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    /* Object Data Fields */
    private int total;
    private int streak;

    /* Constructors */
    /**
     * Default Constructor
     */
    public UserScore() {
        // Cumulative Scores
        total = 0; // lifetime scores
        streak = 0; // this is what is copied to the game score tables on save
    }

    /**
     * UserScore preferably restored from Database or currentUserSave
     * @param total
     * @param streak
     */
    public UserScore(int total, int streak) {
        this.total = total;
        this.streak = streak;
    }

    /* Getters and Setters */
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    public int getStreak() { return streak; }
    public void setStreak(int streak) { this.streak = streak; }

    /**
     * Run through the scores and add or subtract the totals.
     */
    public void breakStreak() {
        // TODO figure this out
    }

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
        System.out.println("USER SCORE: no Object data");
    }
}
