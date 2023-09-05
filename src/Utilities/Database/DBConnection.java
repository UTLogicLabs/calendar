package Utilities.Database;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection
 * Holds database connection information and connection data.
 */
public class DBConnection {
    /** URL of database to connect to. */
    private static final String URL = "jdbc:mysql://wgudb.ucertify.com:3306/WJ07okc";
    /** Username for database connection. */
    private static final String USER = "U07okc";
    /** Password for database connection. */
    private static final String PASSWORD = "53689087951";
    /** Connection Object to save connection data. */
    private static Connection connection;

    /**
     * Gets connection data if already created, otherwise creates a new connection.
     * @return Connection to database.
     */
    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new Driver());
            if(connection == null || connection.isClosed())
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return connection;
        } catch (SQLException ex) {
            throw new RuntimeException("Error Connecting to the Database", ex);
        }
    }

    /**
     * Closes connection to database.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Error Closing the Connection to the Database", ex);
        }
    }
}
