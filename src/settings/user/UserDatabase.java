package settings.user;

import java.sql.*;

public class UserDatabase {
    private static String dbUrl = "jdbc:h2:./src/assets/userdata/userDatabase";
    private static String dbUser = "admin";
    private static String dbPassword = "admin";
    private static String tUsers = "Users";
    private static String tUserScore = "UserScore";
    private static String tUserSettings = "UserSettings";

    public static void makeDatabase() {
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
//            System.out.println("USER DATABASE: " + connection.toString());
            Statement statement = connection.createStatement();

            // TODO Add scores columns to UserScore in the Game
            String sql = "CREATE TABLE IF NOT EXISTS " + tUsers + " ("
                    + " id int AUTO_INCREMENT PRIMARY KEY,"
                    + "	username varchar(50) NOT NULL,"
                    + "	password varchar(50) NOT NULL"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS " + tUserScore + " ("
                    + " id int," // Both Primary and Foreign Key
                    + " total int DEFAULT 0,"
                    + " streak int DEFAULT 0,"
                    + " hangman int DEFAULT 0,"
                    + " hangmanTOTAL int DEFAULT 0,"
                    + " minesweeper int DEFAULT 0,"
                    + " minesweeperTOTAL in DEFAULT 0,"
                    + " PRIMARY KEY (id),"
                    + " FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS " + tUserSettings + " ("
                    + " id int," // Both Primary and Foreign Key
                    + " rememberUser varchar(16) DEFAULT \"false\","
                    + " rememberPassword varchar(16) DEFAULT \"false\","
                    + " gameDifficulty varchar(16) DEFAULT \"NORMAL\","
                    + " PRIMARY KEY (id),"
                    + " FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE"
                    + ");";

            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean saveUser(String name, String password) {
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
//            System.out.println(connection.toString());
            Statement statement = connection.createStatement();

            String sql = "INSERT INTO " + tUsers + " (name,password)"
                    + "VALUES (" + name + "," + password + ")"
                    + ";";

            statement.executeUpdate(sql);
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




}