package Utilities.Database;

import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.Optional;

public class UserDao implements DAO<User>{

    /**
     *
     * @param results
     * @return
     * @throws SQLException
     */
    private User extractFromResults(ResultSet results) throws SQLException {
        return new User(results.getInt("User_ID"), results.getString("User_Name"));
    }

    /**
     * Retrieves single user to login based on username and password.
     * @param username Username of user to login
     * @param password Users password to login
     * @return An Optional either empty or containing a specific user.
     */
    public Optional<User> getUserByUserNameAndPassword(String username, String password) {
        try(Connection connection = DBConnection.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT User_ID, User_Name FROM users WHERE User_Name=? AND Password=?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();

            if(results.next()) {
                return Optional.of(extractFromResults(results));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Retrieves single user based on user id.
     * @param id ID of User to retrieve.
     * @return An Optional either empty or containing a specific User.
     */
    @Override
    public Optional<User> get(int id) {
        try(Connection connection = DBConnection.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT User_ID, User_Name FROM users WHERE id=" + id);

            if(results.next()) {
                return Optional.of(extractFromResults(results));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Retrieves all users from database.
     * @return ObservableList of all Users retrieved from database.
     */
    @Override
    public ObservableList<User> getAll() {
        try(Connection connection = DBConnection.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT User_ID, User_Name FROM users");

            ObservableList<User> users = FXCollections.observableArrayList();

            while(results.next()) {
                User user = extractFromResults(results);
                users.add(user);
            }

            return users;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return FXCollections.observableArrayList();
    }

    /**
     * Inserts a Single user into the database.
     * This feature is not implemented at this time.
     * @param user User Object to insert into database.
     * @return True if User was successfully inserted, otherwise false.
     */
    @Override
    public boolean insert(User user) {
        return false;
    }

    /**
     * Updates a single User in database.
     * This feature is not implemented at this time.
     * @param user User to update in database.
     * @return True if user was successfully updated, otherwise false.
     */
    @Override
    public boolean update(User user) {
        return false;
    }

    /**
     * Deletes a single User from database.
     * This feature is not implemented at this time.
     * @param id ID of User to delete.
     * @return True if User was successfully deleted, otherwise false.
     */
    @Override
    public boolean delete(int id) {
        return false;
    }
}