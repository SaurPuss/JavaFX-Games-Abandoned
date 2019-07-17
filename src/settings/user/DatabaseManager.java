package settings.user;

import settings.user.settings.GameDifficulty;
import settings.user.user.*;

import java.sql.*;

public class DatabaseManager {
    // H2 console JDBC URL: jdbc:h2:.././src/assets/userdata/userDatabase
    private static String dbUrl = "jdbc:h2:./src/assets/userdata/userDatabase";
    private static String dbUser = "admin";
    private static String dbPassword = "admin";

    // Tables
    private static String tUsers = "Users";
    private static String tUserSettings = "User_Settings";
    private static String tUserScore = "User_Score";
    private static String tHangmanScores = "Score_Hangman"; // high streak per name & game mode
    private static String tMastermindScores = "Score_MasterMind";
    private static String tMinesweeperScores = "Score_MineSweeper";
    private static String tSnakeScores = "Score_Snake";

    static boolean findUser(String name) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String input    = "SELECT CASE WHEN EXISTS ("
                            + " SELECT TOP 1 * FROM " + tUsers
                            + " WHERE name = ?) "
                            + "THEN CAST (1 AS BIT) "
                            + "ELSE CAST (0 AS BIT) END";

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
            String input    = "INSERT INTO " + tUsers + " (name,password)"
                            + " VALUES (?,?);"
                            + "INSERT INTO " + tUserSettings + " (id,remember_User)"
                            + " VALUES (LAST_INSERT_ID(),?);"
                            + "INSERT INTO " + tUserScore + " (id)"
                            + " VALUES (LAST_INSERT_ID());";

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
            String getID    = "SELECT (id) FROM " + tUsers
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
            String getData  = "SELECT * FROM " + tUsers + "\n"
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
                    rs.getString("GAME_DIFFICULTY"));

            UserScore userScore = new UserScore(
                    rs.getInt("TOTAL"),
                    rs.getInt("STREAK"),
                    rs.getString("STREAK_GAME"),
                    GameDifficulty.fromString(rs.getString("STREAK_DIFFICULTY")),
                    new int[] { rs.getInt("HANGMAN_EASY"),
                                rs.getInt("HANGMAN_NORMAL"),
                                rs.getInt("HANGMAN_HARD")},
                    new int[] { rs.getInt("MASTERMIND_EASY"),
                                rs.getInt("MASTERMIND_NORMAL"),
                                rs.getInt("MASTERMIND_HARD")},
                    new int[] { rs.getInt("MINESWEEPER_EASY"),
                                rs.getInt("MINESWEEPER_NORMAL"),
                                rs.getInt("MINESWEEPER_HARD")},
                    new int[] { rs.getInt("SNAKE_EASY"),
                                rs.getInt("SNAKE_NORMAL"),
                                rs.getInt("SNAKE_HARD")});

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
        String name    = retrieveName(id);
        boolean rename = renameUserScores(name);
        boolean remove = removeUser(id);

        // rename user to anonymous in scores table
        if (!rename) {
            System.out.println("DB MANAGER: Failed to rename records to anonymous");
            return false;
        } else if (!remove) {
            System.out.println("DB MANAGER: Failed to remove records from database");
            return false;
        } else {
            System.out.println("DB MANAGER: Looks like user deletion was a success");
            return true;
        }
    }

    private static boolean renameUserScores(String name) {
        // Return true if no records (false on search) are found
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String input    = "DECLARE @OldName varchar(50);"
                            + "SET @OldName = ?;"
                            + "UPDATE " + tHangmanScores
                            + " SET name = 'Anonymous'"
                            + " WHERE name = @OldName;"
                            + "UPDATE " + tMastermindScores
                            + " SET name = 'Anonymous'"
                            + " WHERE name = @OldName;"
                            + "UPDATE " + tMinesweeperScores
                            + " SET name = 'Anonymous'"
                            + " WHERE name = @OldName;"
                            + "UPDATE " + tSnakeScores
                            + " SET name = 'Anonymous'"
                            + " WHERE name = @OldName;";

            PreparedStatement sDEL = connection.prepareStatement(input);
            sDEL.setString(1, name);
            ResultSet rs = sDEL.executeQuery();
            rs.next();

            // Check with scores tables
            String[] tables = {tHangmanScores, tMastermindScores,
                    tMinesweeperScores, tSnakeScores};
            for (String s : tables) {
                String output   = "SELECT CASE WHEN EXISTS ("
                                + " SELECT TOP 1 * FROM " + s
                                + " WHERE name = ?) "
                                + "THEN CAST (1 AS BIT) "
                                + "ELSE CAST (0 AS BIT) END";

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
            String delete   = "DELETE FROM ("
                            + " SELECT * FROM " + tUsers
                            + " WHERE id = ?) "
                            + "THEN CAST (1 AS BIT) "
                            + "ELSE CAST (0 AS BIT) END";

            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();

            return rs.getBoolean(1);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update a full user profile that already exists in the database, not including username or password
     * @param user
     * @return
     */
    static boolean updateUser(User user) {
        if (!findUser(user.getName())) {
            System.out.println("DB MANAGER: User does not exist, can't update records.");
            return false;
        }

        boolean score = updateUserScore(user.getId(), user.getUserScore());
        boolean settings = updateUserSettings(user.getId(), user.getUserSettings());

        return score && settings;
    }

    static boolean updateUserName(long id, String name, String newName) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            if (findUser(newName)) {
                System.out.println("DB MANAGER: Username already taken");
                return false;
            }

            String input    = "UPDATE " + tUsers + " SET name = ?"
                            + " WHERE id = ? AND name = ?";
            PreparedStatement statement = connection.prepareStatement(input);
            statement.setString(1, newName);
            statement.setLong(2, id);
            statement.setString(3, name);
            statement.executeQuery();

            String output   = "SELECT CASE WHEN EXISTS ("
                            + " SELECT TOP 1 * FROM " + tUsers
                            + " WHERE name = ? AND id = ?) "
                            + "THEN CAST (1 AS BIT) "
                            + "ELSE CAST (0 AS BIT) END";
            statement = connection.prepareStatement(output);
            statement.setString(1, newName);
            statement.setLong(2, id);
            ResultSet rs = statement.executeQuery();
            rs.next();

            return rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean updatePassword(long id, String password, String newPassword) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            // TODO the hashing stuff
            String input    = "UPDATE " + tUsers + " SET password = ?"
                            + " WHERE id = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(input);
            statement.setString(1, newPassword);
            statement.setLong(2, id);
            statement.setString(3, password);
            statement.executeQuery();

            String output   = "SELECT CASE WHEN EXISTS ("
                            + " SELECT TOP 1 * FROM " + tUsers
                            + " WHERE password = ? AND id = ?) "
                            + "THEN CAST (1 AS BIT) "
                            + "ELSE CAST (0 AS BIT) END";
            statement = connection.prepareStatement(output);
//            statement.setString(1, newPassword);
//            statement.setLong(2, id);
            ResultSet rs = statement.executeQuery();
            rs.next();

            return rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update user settings tied to a profile already existing in the db
     * @param id
     * @param userSettings
     * @return
     */
    static boolean updateUserSettings(long id, UserSettings userSettings) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String input    = "UPDATE " + tUserSettings + " SET"
                            + " remember_user = ?, remember_password = ?,"
                            + " color_Mode = ?, game_Difficulty = ? "
                            + "WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(input);
            statement.setString(1, String.valueOf(userSettings.isRememberUser()));
            statement.setString(2, String.valueOf(userSettings.isRememberPassword()));
            statement.setString(3, userSettings.getColorMode().toString());
            statement.setString(4, userSettings.getGameDifficulty().toString());
            statement.setLong(5, id);
            statement.executeUpdate();

            // TODO bool check?
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean updateUserScore(long id, UserScore userScore) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String input    = "UPDATE " + tUserScore + " SET"
                            + " total = ?, streak = ?, streak_Game = ?, streak_Difficulty = ?,"
                            + " hangman_EASY = ?, hangman_NORMAL = ?, hangman_HARD = ?,"
                            + " mastermind_EASY = ?, mastermind_NORMAL = ?, mastermind_HARD = ?,"
                            + " minesweeper_EASY = ?, minesweeper_NORMAL = ?, minesweeper_HARD = ?,"
                            + " snake_EASY = ?, snake_NORMAL = ?, snake_HARD = ? "
                            + "WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(input);
            statement.setInt(1, userScore.getTotal());
            statement.setInt(2, userScore.getStreak());
            statement.setString(3, userScore.getStreakGame());
            statement.setString(4, userScore.getStreakDifficulty().toString());
            statement.setInt(5, userScore.getHangman()[0]);
            statement.setInt(6, userScore.getHangman()[1]);
            statement.setInt(7, userScore.getHangman()[2]);
            statement.setInt(8, userScore.getMastermind()[0]);
            statement.setInt(9, userScore.getMastermind()[1]);
            statement.setInt(10, userScore.getMastermind()[2]);
            statement.setInt(11, userScore.getMinesweeper()[0]);
            statement.setInt(12, userScore.getMinesweeper()[1]);
            statement.setInt(13, userScore.getMinesweeper()[2]);
            statement.setInt(14, userScore.getSnake()[0]);
            statement.setInt(15, userScore.getSnake()[1]);
            statement.setInt(16, userScore.getSnake()[2]);
            statement.setLong(17, id);
            statement.executeUpdate();

            // TODO bool check?
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void makeDatabase() {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql  = "CREATE TABLE IF NOT EXISTS " + tUsers + " ("
                        + " id int AUTO_INCREMENT PRIMARY KEY,"
                        + "	name varchar(50) UNIQUE NOT NULL,"
                        + "	password varchar(50) NOT NULL"
                        + ");\n"
                        + "CREATE TABLE IF NOT EXISTS " + tUserSettings + " ("
                        + " id int NOT NULL," // Both Primary and Foreign Key
                        + " remember_User bit DEFAULT 'false',"
                        + " remember_Password bit DEFAULT 'false',"
                        + " color_Mode varchar(16) DEFAULT 'Light',"
                        + " game_Difficulty varchar(16) DEFAULT 'Normal',"
                        + " PRIMARY KEY (id),"
                        + " FOREIGN KEY (id) REFERENCES " + tUsers + "(id) ON DELETE CASCADE"
                        + ");\n"
                        + "CREATE TABLE IF NOT EXISTS " + tUserScore + " ("
                        + " id int NOT NULL," // Both Primary and Foreign Key
                        + " total int DEFAULT 0," // All time across games
                        + " streak int DEFAULT 0," // Reset to 0 after a loss or end game
                        + " streak_Game varchar(50) DEFAULT 'hangman',"
                        + " streak_Difficulty varchar(16) DEFAULT 'Normal',"
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
                        + " FOREIGN KEY (id) REFERENCES " + tUsers + "(id) ON DELETE CASCADE"
                        + ");\n"
                        + "CREATE TABLE IF NOT EXISTS " + tHangmanScores + " ("
                        + " name varchar(50) DEFAULT 'Anonymous',"
                        + " mode varchar(16) DEFAULT 'Normal',"
                        + " score int DEFAULT 0"
                        + ");\n"
                        + "CREATE TABLE IF NOT EXISTS " + tMastermindScores + " ("
                        + " name varchar(50) DEFAULT 'Anonymous',"
                        + " mode varchar(16) DEFAULT 'Normal',"
                        + " score int DEFAULT 0"
                        + ");\n"
                        + "CREATE TABLE IF NOT EXISTS " + tMinesweeperScores + " ("
                        + " name varchar(50) DEFAULT 'Anonymous',"
                        + " mode varchar(16) DEFAULT 'Normal',"
                        + " score int DEFAULT 0"
                        + ");\n"
                        + "CREATE TABLE IF NOT EXISTS " + tSnakeScores + " ("
                        + " name varchar(50) DEFAULT 'Anonymous',"
                        + " mode varchar(16) DEFAULT 'Normal',"
                        + " score int DEFAULT 0"
                        + ");\n";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}