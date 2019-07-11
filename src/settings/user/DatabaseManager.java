package settings.user;

import settings.user.user.*;

import java.sql.*;

public class DatabaseManager {
    // H2 console JDBC URL: jdbc:h2:.././src/assets/userdata/userDatabase
    private static String dbUrl = "jdbc:h2:./src/assets/userdata/userDatabase";
    private static String dbUser = "admin";
    private static String dbPassword = "admin";

    private static String tUsers = "Users";
    private static String tUserScore = "UserScore";
    private static String tUserSettings = "UserSettings";

    public static void makeDatabase() {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            Statement statement = connection.createStatement();
            // TODO Add scores columns to UserScore in the Game
            String sql = "CREATE TABLE IF NOT EXISTS " + tUsers + " ("
                    + " id int AUTO_INCREMENT PRIMARY KEY,"
                    + "	username varchar(50) UNIQUE NOT NULL,"
                    + "	password varchar(50) NOT NULL"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS " + tUserScore + " ("
                    + " id int NOT NULL," // Both Primary and Foreign Key
                    + " total int DEFAULT 0,"
                    + " streak int DEFAULT 0,"
                    + " hangman int DEFAULT 0,"
                    + " hangmanTOTAL int DEFAULT 0,"
                    + " minesweeper int DEFAULT 0,"
                    + " minesweeperTOTAL int DEFAULT 0,"
                    + " PRIMARY KEY (id),"
                    + " FOREIGN KEY (id) REFERENCES " + tUsers + "(id) ON DELETE CASCADE,"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS " + tUserSettings + " ("
                    + " id int NOT NULL," // Both Primary and Foreign Key
                    + " rememberUser bit DEFAULT 'false',"
                    + " rememberPassword bit DEFAULT 'false',"
                    + " gameDifficulty varchar(16) DEFAULT 'Normal',"
                    + " PRIMARY KEY (id),"
                    + " FOREIGN KEY (id) REFERENCES " + tUsers + "(id) ON DELETE CASCADE,"
                    + ");";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static boolean findUser(String name) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            Statement statement = connection.createStatement();
            String input = "SELECT CASE WHEN EXISTS ("
                    + " SELECT TOP 1 * FROM " + tUsers
                    + " WHERE username = '" + name + "')"
                    + " THEN CAST (1 AS BIT)"
                    + " ELSE CAST (0 AS BIT) END";

            ResultSet rs = statement.executeQuery(input);
            rs.next();

            return rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static String getUserPassword(String name) {
        // TODO when the password is hashed, hash input and compare server side
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            Statement statement = connection.createStatement();
            String output = "SELECT (password) FROM " + tUsers + " WHERE username = '" + name + "';";
            ResultSet rs = statement.executeQuery(output);
            rs.next();

            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static User saveUser(String name, String password, boolean rememberUser) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)){
            // Insert new user into database
            Statement statement = connection.createStatement();
            String input = "INSERT INTO " + tUsers + " (username,password)"
                    + " VALUES ('" + name + "','" + password + "');"
                    + "INSERT INTO " + tUserScore + " (id) VALUES (LAST_INSERT_ID());"
                    + "INSERT INTO " + tUserSettings + " (id,rememberUser)"
                    + " VALUES (LAST_INSERT_ID(), '" + rememberUser + "');";
            statement.executeUpdate(input);

            // retrieve user ID
            String output = "SELECT (id) FROM " + tUsers + " WHERE username = '" + name + "';";
            ResultSet rs = statement.executeQuery(output);
            rs.next();

            return new User(rs.getLong(1), name, password, rememberUser);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static User retrieveUser(String name, String password) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            Statement statement = connection.createStatement();

            // get matching user id assuming name and password are valid
            String getID = "SELECT (id) FROM " + tUsers
                    + " WHERE username = '" + name + "'"
                    + " AND password = '" + password + "';";
            ResultSet rs = statement.executeQuery(getID);
            rs.next();
            long id = rs.getLong(1);

            // retrieve user profile based on id
            String getData = "SELECT * FROM " + tUsers + "\n"
                    + "INNER JOIN " + tUserSettings
                    + " ON " + tUserSettings + ".id = " + tUsers + ".id\n"
                    + "INNER JOIN " + tUserScore
                    + " ON " + tUserScore + ".id = " + tUsers + ".id\n"
                    + "WHERE " + tUsers + ".id = " + id + ";";
            rs = statement.executeQuery(getData);
            rs.next();

            // Populate user
            UserSettings userSettings = new UserSettings(
                    rs.getBoolean("REMEMBERUSER"),
                    rs.getBoolean("REMEMBERPASSWORD"),
                    rs.getString("GAMEDIFFICULTY")
            );
            UserScore userScore = new UserScore(
                    rs.getInt("TOTAL"),
                    rs.getInt("STREAK"),
                    rs.getInt("HANGMAN"),
                    rs.getInt("HANGMANTOTAL"),
                    rs.getInt("MINESWEEPER"),
                    rs.getInt("MINESWEEPERTOTAL")

            );
            return new User(
                    rs.getLong(1),
                    rs.getString("USERNAME"),
                    rs.getString("PASSWORD"),
                    userSettings,
                    userScore);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static boolean deleteUser(long id) {
        // copy user scores to Anonymous default user
        System.out.println("DB MANAGER: Copied records to anonymous: "
                + retainUserScores(id));

        // remove user from the database


        return false;
    }

    private static boolean retainUserScores(long id) {
        // Make a copy of the scores records to anonymous/unknown

        return false;
    }

    static boolean updateUserSettings(long id, UserSettings userSettings) {

        return false;
    }

    static boolean updateUserScore(String game, long id, int score) {
        // switch statement




        return false;
    }
}