package settings.user;

import settings.user.settings.GameDifficulty;
import settings.user.user.*;

import java.sql.*;

public class DatabaseManager {
    // H2 console JDBC URL: jdbc:h2:.././src/assets/userdata/userDatabase
    private static String dbUrl = "jdbc:h2:./src/assets/userdata/userDatabase";
    private static String dbUser = "admin";
    private static String dbPassword = "admin";

    // User Tables
    private static String tUsers = "Users";
    private static String tUserSettings = "UserSettings";
    private static String tUserScore = "UserScore";

    // HighScore Tables
    private static String tHangmanScores = "HangmenScores"; // session streak per name & game mode
    private static String tMastermindScores = "MasterMindScores";
    private static String tMinesweeperScores = "MineSweeperScores";
    private static String tSnakeScores = "SnakeScores";



    static boolean findUser(String name) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String input = "SELECT CASE WHEN EXISTS ("
                    + " SELECT TOP 1 * FROM " + tUsers
                    + " WHERE name = ?)"
                    + " THEN CAST (1 AS BIT)"
                    + " ELSE CAST (0 AS BIT) END";
            PreparedStatement statement = connection.prepareStatement(input);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
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
            String input = "SELECT (password) FROM " + tUsers + " WHERE name = ?;";
            PreparedStatement statement = connection.prepareStatement(input);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            rs.next();

            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create new user in tables: Users, UserSettings, UserScores, Scores
     * @param name user name
     * @param password user password
     * @param rememberUser user remember
     * @return User object with relevant data that mirrors what was inserted
     *              into the database.
     */
    static User saveUser(String name, String password, boolean rememberUser) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            // Insert new user into database
            String input = "INSERT INTO " + tUsers + " (name,password)"
                    + " VALUES (?,?);"
                    + "INSERT INTO " + tUserSettings + " (id,remember_User)"
                    + " VALUES (LAST_INSERT_ID(),?);"
                    + "INSERT INTO " + tUserScore + " (id) VALUES (LAST_INSERT_ID());";
            PreparedStatement sIN = connection.prepareStatement(input);
            sIN.setString(1, name);
            sIN.setString(2, password);
            sIN.setString(3, String.valueOf(rememberUser));
            sIN.executeUpdate(input);

            // retrieve user ID
            String output = "SELECT (id) FROM " + tUsers + " WHERE name = ?;";
            PreparedStatement sOUT = connection.prepareStatement(output);
            sOUT.setString(1, name);
            ResultSet rs = sOUT.executeQuery();
            rs.next();

            return new User(rs.getLong(1), name, password, rememberUser);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static long retrieveID(String name, String password) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            // get matching user id assuming name and password are valid
            String getID = "SELECT (id) FROM " + tUsers
                    + " WHERE name = ? AND password = ?;";
            PreparedStatement sID = connection.prepareStatement(getID);
            sID.setString(1, name);
            sID.setString(2, password);
            ResultSet rs = sID.executeQuery();
            rs.next();

            return rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static String retrieveName(long id) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String input = "SELECT (name) FROM " + tUsers + " WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(input);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();

            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static User retrieveUser(String name, String password) {
        long id = retrieveID(name, password);

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            // retrieve user profile based on ID
            String getData = "SELECT * FROM " + tUsers + "\n"
                    + "INNER JOIN " + tUserSettings
                    + " ON " + tUserSettings + ".id = " + tUsers + ".id\n"
                    + "INNER JOIN " + tUserScore
                    + " ON " + tUserScore + ".id = " + tUsers + ".id\n"
                    + "WHERE " + tUsers + ".id = ?;";
            PreparedStatement sDATA = connection.prepareStatement(getData);
            sDATA.setLong(1, id);
            ResultSet rs = sDATA.executeQuery();
            rs.next();

            // Populate User
            UserSettings userSettings = new UserSettings(
                    rs.getBoolean("REMEMBER_USER"),
                    rs.getBoolean("REMEMBER_PASSWORD"),
                    rs.getString("GAME_DIFFICULTY")
            );
            UserScore userScore = new UserScore(
                    rs.getInt("TOTAL"),
                    rs.getInt("STREAK"),
                    rs.getString("STREAK_GAME"),
                    GameDifficulty.fromString(rs.getString("STREAK_DIFFICULTY")),
                    new int[] {rs.getInt("HANGMAN_EASY"),
                            rs.getInt("HANGMAN_NORMAL"),
                            rs.getInt("HANGMAN_HARD")},
                    new int[] {rs.getInt("MASTERMIND_EASY"),
                            rs.getInt("MASTERMIND_NORMAL"),
                            rs.getInt("MASTERMIND_HARD")},
                    new int[] {rs.getInt("MINESWEEPER_EASY"),
                            rs.getInt("MINESWEEPER_NORMAL"),
                            rs.getInt("MINESWEEPER_HARD")},
                    new int[] {rs.getInt("SNAKE_EASY"),
                            rs.getInt("SNAKE_NORMAL"),
                            rs.getInt("SNAKE_HARD")}
            );
            return new User(
                    rs.getLong(1),
                    rs.getString("NAME"),
                    rs.getString("PASSWORD"),
                    userSettings,
                    userScore);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static boolean deleteUser(long id) {
        // TODO Maybe make this a toggle option, remove or anonymize your scores? or only remove account?
        // input id, return username for renaming in scores
        String name = retrieveName(id);
        boolean rename = renameUserScores(name);
        boolean remove = removeUser(id);

        // rename user to anonymous in scores table
        if (!rename) {
            System.out.println("DB MANAGER: Failed to rename records to anonymous");
            return false;
        } else if (!remove) {
            System.out.println("DB MANAGER: Failed to remove records from database");
            return false;
        } else
            return true;
    }

    private static boolean renameUserScores(String name) {
        // Return true if no records (false on search) are found
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String input = "UPDATE " + tHangmanScores
                    + " SET name = 'Anonymous'"
                    + " WHERE name = ?;"
                    + "UPDATE " + tMastermindScores
                    + " SET name = 'Anonymous'"
                    + " WHERE name = ?;"
                    + "UPDATE " + tMinesweeperScores
                    + " SET name = 'Anonymous'"
                    + " WHERE name = ?;"
                    + "UPDATE " + tSnakeScores
                    + " SET name = 'Anonymous'"
                    + " WHERE name = ?;";

            PreparedStatement sDEL = connection.prepareStatement(input);
            sDEL.setString(1, name); // TODO Can I turn this into a single statement?
            sDEL.setString(1, name);
            sDEL.setString(1, name);
            sDEL.setString(1, name);
            ResultSet rs = sDEL.executeQuery();
            rs.next();

            // Check with scores tables
            String[] tables = {tHangmanScores, tMastermindScores,
                    tMinesweeperScores, tSnakeScores};
            for (String s : tables) {
                String output = "SELECT CASE WHEN EXISTS ("
                        + " SELECT TOP 1 * FROM " + s
                        + " WHERE name = ?)"
                        + " THEN CAST (1 AS BIT)"
                        + " ELSE CAST (0 AS BIT) END";
                PreparedStatement sCHECK = connection.prepareStatement(output);
                sCHECK.setString(1, name);
                rs = sCHECK.executeQuery();
                rs.next();

                // Break loop if anything returns true
                if (rs.getBoolean(1)) {
                    // flip bool if something is found return false
                    return !rs.getBoolean(1);
                }
            }

            // flip the bool
            return !rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean removeUser(long id) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String delete = "DELETE FROM ("
                    + " SELECT * FROM " + tUsers
                    + " WHERE id = ?)"
                    + " THEN CAST (1 AS BIT)"
                    + " ELSE CAST (0 AS BIT) END";
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();

            return rs.getBoolean(1);
            // return sql on removal boolean
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean updateUserSettings(long id, UserSettings userSettings) {


        return false;
    }

    static boolean updateUserScore(long id, UserScore userScore) {
        // switch statement




        return false;
    }



    public static void makeDatabase() {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            Statement statement = connection.createStatement();
            // TODO Add scores columns to UserScore in the Game
            String sql = "CREATE TABLE IF NOT EXISTS " + tUsers + " ("
                    + " id int AUTO_INCREMENT PRIMARY KEY,"
                    + "	name varchar(50) UNIQUE NOT NULL,"
                    + "	password varchar(50) NOT NULL"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS " + tUserScore + " ("
                    + " id int NOT NULL," // Both Primary and Foreign Key
                    + " total int DEFAULT 0," // All time across games
                    + " streak int DEFAULT 0," // Reset to 0 after a loss or end game
                    + " streak_Game varchar(50) DEFAULT 'hangman'"
                    + " streak_Difficulty varchar(10) DEFAULT 'Normal'"
                    + " hangman_EASY int DEFAULT 0,"
                    + " hangman_NORMAL int DEFAULT 0,"
                    + " hangman_HARD int DEFAULT 0,"
                    + " mastermind_EASY int DEFAULT 0,"
                    + " mastermind_NORMAL int DEFAULT 0,"
                    + " mastermind_HARD int DEFAULT 0,"
                    + " minesweeper_EASY int DEFAULT 0,"
                    + " minesweeper_NORMAL int DEFAULT 0,"
                    + " minesweeper_HARD int DEFAULT 0,"
                    + " snake_EASY int DEFAULT 0,"
                    + " snake_NORMAL int DEFAULT 0,"
                    + " snake_HARD int DEFAULT 0,"
                    + " PRIMARY KEY (id),"
                    + " FOREIGN KEY (id) REFERENCES " + tUsers + "(id) ON DELETE CASCADE,"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS " + tUserSettings + " ("
                    + " id int NOT NULL," // Both Primary and Foreign Key
                    + " remember_User bit DEFAULT 'false',"
                    + " remember_Password bit DEFAULT 'false',"
                    + " game_Difficulty varchar(16) DEFAULT 'Normal',"
                    + " PRIMARY KEY (id),"
                    + " FOREIGN KEY (id) REFERENCES " + tUsers + "(id) ON DELETE CASCADE,"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS " + tHangmanScores + " ("
                    + " name varchar(50) DEFAULT 'Anonymous',"
                    + " mode varchar(10) DEFAULT 'Normal',"
                    + " score int DEFAULT 0,"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS " + tMastermindScores + " ("
                    + " name varchar(50) DEFAULT 'Anonymous',"
                    + " mode varchar(10) DEFAULT 'Normal',"
                    + " score int DEFAULT 0,"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS " + tMinesweeperScores + " ("
                    + " name varchar(50) DEFAULT 'Anonymous',"
                    + " mode varchar(10) DEFAULT 'Normal',"
                    + " score int DEFAULT 0,"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS " + tSnakeScores + " ("
                    + " name varchar(50) DEFAULT 'Anonymous',"
                    + " mode varchar(10) DEFAULT 'Normal',"
                    + " score int DEFAULT 0,"
                    + ");";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}