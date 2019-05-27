package settings.user;

import java.io.*;

// TODO password hashing and encryption
public class UserManager implements Serializable {
    private static String currentUserFile = "src/assets/currentUser.dat";
    private static String allUsersFilePath = "src/assets/users.csv";

    /**
     * Check if a user already exists in the users.csv
     * @param userName Username to check against
     * @return Yay or Nay
     */
    public static boolean findUser(String userName) {
        BufferedReader reader;
        try {
            String line;
            reader = new BufferedReader(new FileReader(allUsersFilePath));
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length > 0) {
                    // Yaya, lowercase all of that shit
                    if (userName.toLowerCase().equals(fields[1].toLowerCase())) {
                        return true;
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Try to retrieve a user from currentUser.dat, if failed return default.
     * @return Existing or Default User object
     */
    public static User getCurrentUser() {
        User user = new User();

        try {
            FileInputStream f = new FileInputStream(currentUserFile);
            ObjectInputStream o = new ObjectInputStream(f);

            System.out.println("Existing current user found.");
            user = (User) o.readObject();

            o.close();
        } catch (FileNotFoundException e) {
            System.out.println("currentUser.dat does not exist. Creating new file with default user.");
            saveCurrentUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Save user to currentUser.dat
     * @param user User to be saved
     */
    public static void saveCurrentUser(User user) {
        try {
            FileOutputStream f = new FileOutputStream(currentUserFile, false);
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set currentUser.dat to default user, wiping existing record.
     */
    public static void deleteCurrentUser() {
        saveCurrentUser(new User());
    }













    /**
     * Save User to users.csv, assuming it's a unique username and has a password
     * @param user User input to potentially save
     */
    public static void saveNewUser(User user) {
        FileWriter fileWriter;
        try {
            File check = new File(allUsersFilePath);
            fileWriter = new FileWriter(allUsersFilePath, true);

            // Add a header if this is a new file
            if (!check.exists()) {
                fileWriter.append("Username,Password,TotalScore,CurrentStreak,HighestStreak\n");
            }

            // Add the user information to the file
            fileWriter.append(user.getUserName());
            fileWriter.append(',');
            fileWriter.append(user.getUserPassword());
            fileWriter.append(',');
            fileWriter.append(String.valueOf(user.getUserScore().getTotalScore()));
            fileWriter.append(',');
            fileWriter.append(String.valueOf(user.getUserScore().getCurrentScore()));
            fileWriter.append(',');
            fileWriter.append(String.valueOf(user.getUserScore().getHighestStreak()));
            fileWriter.append(',');
            fileWriter.append(String.valueOf(user.isRememberPassword()));
            fileWriter.append("\n");

            System.out.println("User has been saved");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convenience method that invokes the saveNewUser(User) method
     * @param username Making a new user with this username
     * @param password Adding a password to be saved too
     */
    public static void saveNewUser(String username, String password) {
        User user = new User(username, password);
        saveNewUser(user);
    }



    /**
     *
     * @param userName
     * @param userPassword
     * @return if match return user, else return default
     */
    public static User retrieveUser(String userName, String userPassword) {
        User user = new User();

        if (!findUser(userName)) {
            System.out.println("This user does not exist, please make a new one");
            return user;
        } else {
            BufferedReader reader;
            // retrieve user
            try {
                String line;
                reader = new BufferedReader(new FileReader(allUsersFilePath));
                reader.readLine();

                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");

                    if (fields.length > 0) {
                        // TODO make password check method instead of userPassword.equals(fields[2]))
                        if ((userName.equals(fields[1])) && (userPassword.equals(fields[2]))) {
                            user.setUserName(fields[1]);
                            user.setUserPassword(fields[2]);
                            user.getUserScore().setSavedTotalScore(Integer.valueOf(fields[3]));
                            user.getUserScore().resetCurrentScore();
                            user.getUserScore().setSavedHighestStreak(Integer.valueOf(fields[5]));
                            user.setRememberPassword(Boolean.valueOf(fields[6]));
                        } else {
                            // TODO Make this a real thing
                            System.out.println("Password does not match, try again");
                        }
                    }
                }

                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return user;
        }
    }

    public static User getUserFromFile(int n) {
        User user = new User();
        if (n == 1) {
            // Do stuff with the currentUser.csv file

            return getCurrentUser();
        } else {
            // Try to make a user from users.csv

            return user;
        }
    }




    // TODO refactor n shit
    /*public static void readCSV() {
        BufferedReader reader = null;

        try {
            List<User> users = new ArrayList<>();
            String line;
            reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length > 0) {
                    User user = new User();
                    user.setUserName(fields[1]);
                    user.setUserPassword(fields[2]);
                    user.setCurrentScore(Integer.valueOf(fields[3]));
                    user.setHighestStreak(Integer.valueOf(fields[4]));
                    user.setTotalScore(Integer.valueOf(fields[5]));

                    users.add(user);
                }
            }

            for (User u : users) {
                System.out.println("------------------");
                System.out.println(u.toString());
            }
            System.out.println("------------------");

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
