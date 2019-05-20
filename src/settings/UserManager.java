package settings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;

public class UserManager  implements Serializable {
    private static String filePath = "src/assets/users.csv";

    /**
     *
     * @param user
     */
    public static void saveNewUser(User user) {
        // Do stuff
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(filePath);

            fileWriter.append(user.getUserName());
            fileWriter.append(',');
            fileWriter.append(user.getUserPassword());
            fileWriter.append(',');
            fileWriter.append(String.valueOf(user.getCurrentScore()));
            fileWriter.append(',');
            fileWriter.append(String.valueOf(user.getHighestStreak()));
            fileWriter.append(',');
            fileWriter.append(String.valueOf(user.getTotalScore()));
            fileWriter.append("\n");

            System.out.println("User has been saved");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param userName
     * @return
     */
    private static boolean findUser(String userName) {
        BufferedReader reader;

        try {
            String line;
            reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length > 0) {
                    if (userName.equals(fields[1])) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public User retrieveUser(String userName, String userPassword) {
        User user = new User();
        BufferedReader reader;

        // Check if user exists
        if (!findUser(userName)) {
            System.out.println("This user does not exist, please make a new one");
            return user; // returns a default user
        } else {
            // retrieve user
            try {
                String line;
                reader = new BufferedReader(new FileReader(filePath));
                reader.readLine();

                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");

                    if (fields.length > 0) {
                        // TODO make password check method
                        if ((userName.equals(fields[1])) && (userPassword.equals(fields[2]))) {
                            user.setUserName(fields[1]);
                            user.setUserPassword(fields[2]);
                            user.setCurrentScore(0);
                            user.setHighestStreak(Integer.valueOf(fields[4]));
                            user.setTotalScore(Integer.valueOf(fields[5]));
                        } else {
                            // TODO Make this a real thing
                            System.out.println("Password does not match");
                            return null;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

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
