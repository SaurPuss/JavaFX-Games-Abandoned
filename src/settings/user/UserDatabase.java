package settings.user;

import java.sql.*;

public class UserDatabase {

    public static void makeDatabase() {
        String url = "jdbc:h2:./src/assets/userdata/userDatabase";
        String user = "admin";
        String password = "admin";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println(connection.toString());
            Statement statement = connection.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + " id int AUTOINCREMENT PRIMARY KEY,"
                    + "	username varchar(255) NOT NULL,"
                    + "	password varchar(255) NOT NULL,"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS userScores ("
                    + " scoresID int NOT NULL," // Both Primary and Foreign Key
                    + " totalScore int NOT NULL,"
                    + " highestScore int NOT NULL,"
                    + " PRIMARY KEY (scoresID),"
                    + " FOREIGN KEY (scoresID) REFERENCES users(id) ON DELETE CASCADE"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS userSettings (\n"
                    + " settingsID int NOT NULL," // Both Primary and Foreign Key
                    + " rememberUser varchar(16) NOT NULL,"
                    + " rememberPassword varchar(16) NOT NULL,"
                    + " gameDifficulty varchar(16) NOT NULL,"
                    + " PRIMARY KEY (settingsID),"
                    + " FOREIGN KEY (settingsID) REFERENCES users(id) ON DELETE CASCADE"
                    + ");";



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}