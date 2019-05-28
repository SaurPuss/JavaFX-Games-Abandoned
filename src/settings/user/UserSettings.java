package settings.user;

import java.io.*;

/**
 * User settings go here
 */
class UserSettings implements Serializable {
    private static final long serialVersionUID = 365729905314659904L;

    UserSettings() {
        // Print all the user settings and allow for toggling of remember password etc
        boolean savePassword = false;
    }


    private void writeObject(ObjectOutputStream out) throws IOException {}
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {}
    private void readObjectNoData() throws ObjectStreamException {}

}
