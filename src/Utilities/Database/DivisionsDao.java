package Utilities.Database;

import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * DOA implementation for First Level Divisions (States/Provinces).
 */
public class DivisionsDao implements DAO<Division>{
    /**
     * Extracts Division Object from results.
     * @param results Results containing Division Data.
     * @return Division object to be used.
     */
    private Division extractFromResults(ResultSet results) throws SQLException {
        return new Division(
                results.getInt("Division_ID"),
                results.getString("Division"),
                results.getInt("Country_ID")
        );
    }

    /**
     * Retrieves single Division, based on ID.
     * @param id ID of Division to retrieve.
     * @return Optional Division.
     */
    @Override
    public Optional<Division> get(int id) {
        try (Connection connection = DBConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Division_ID, Division, Country_ID, Country FROM first_level_divisions JOIN countries on first_level_divisions.Country_ID=countries.Country_ID WHERE Division_ID=" + id);

            if(results.next()) {
                return Optional.of(extractFromResults(results));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Retrieves all Divisions in Database.
     * @return ObservableList containing all Divisions retrieved from Database.
     */
    @Override
    public ObservableList<Division> getAll() {
        try (Connection connection = DBConnection.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT first_level_divisions.*, countries.Country FROM first_level_divisions JOIN countries on first_level_divisions.Country_ID=countries.Country_ID");

            ObservableList<Division> divisions = FXCollections.observableArrayList();
            while(results.next()) {
                Division division = extractFromResults(results);
                divisions.add(division);
            }

            return divisions;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return FXCollections.observableArrayList();
    }

    /**
     * Inserts Division into database.
     * This feature is not implemented at this time.
     * @param division Division Object to insert into database.
     * @return True if division was successfully inserted, false otherwise.
     */
    @Override
    public boolean insert(Division division) {
        return false;
    }

    /**
     * Updates a division in the database.
     * This feature is not implemented at this time.
     * @param division Division Object to update in database.
     * @return True if contact was successfully updated, false otherwise.
     */
    @Override
    public boolean update(Division division) {
        return false;
    }

    /**
     * Deletes a single division from database.
     * This feature is not implemented at this time.
     * @param id ID of Division to delete.
     * @return True if contact was successfully deleted, false otherwise.
     */
    @Override
    public boolean delete(int id) {
        return false;
    }
}
