package Utilities.Database;

import Models.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * Creates a Database Access Object for Contacts.
 */
public class ContactDao implements DAO<Contact>{
    /**
     * Extracts Results into a Contact Object.
     * @param results Contains the Contacts to be extracted from the database.
     * @return Contact Object extracted from the results.
     * @throws SQLException
     */
    private Contact extractFromResults(ResultSet results) throws SQLException {
        return new Contact(
                results.getInt("Contact_ID"),
                results.getString("Contact_Name"),
                results.getString("Email")
        );
    }

    /**
     * Retrieves single Contact from database.
     * @param id ID of Contact to retrieve.
     * @return An empty Optional, or containing the contact.
     */
    @Override
    public Optional<Contact> get(int id) {
        Connection connection = DBConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("GET * FROM contacts WHERE Contact_ID=" + id);

            if(results.next()) {
                return Optional.of(extractFromResults(results));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Gets all Contacts from database.
     * @return An ObservableList containing all contacts retrieved from database.
     */
    @Override
    public ObservableList<Contact> getAll() {
        try(Connection connection = DBConnection.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM contacts");

            ObservableList<Contact> contacts = FXCollections.observableArrayList();

            while(results.next()) {
                contacts.add(extractFromResults(results));
            }

            return contacts;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return FXCollections.observableArrayList();
    }

    /**
     * Inserts a contact into database.
     * This feature is not implemented at this time.
     * @param contact Contact to insert into Database.
     * @return True if a contact was successfully added, false otherwise.
     */
    @Override
    public boolean insert(Contact contact) {
        return false;
    }

    /**
     * Updates a contact in the databse.
     * This feature is not implemented at this time.
     * @param contact Contact to update in Database.
     * @return True if contact was successfully updated, false otherwise.
     */
    @Override
    public boolean update(Contact contact) {
        return false;
    }

    /**
     * Deletes a contact in the database.
     * This feature is not implemented at this time.
     * @param id ID of contact to delete from database.
     * @return True if contact was successfully deleted, false otherwise.
     */
    @Override
    public boolean delete(int id) {
        return false;
    }
}
