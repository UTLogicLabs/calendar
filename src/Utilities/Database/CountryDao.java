package Utilities.Database;

import Models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Optional;

/**
 * Creates a Data Access Object for appointments.
 */
public class CountryDao implements DAO<Country> {

    /**
     * Extracts the Country from the ResultSet.
     * @param results Contains the Country data pulled from the database.
     * @return Country extracted from results
     * @throws SQLException
     */
    private Country extractFromResults(ResultSet results) throws SQLException {
        return new Country(results.getInt("Country_ID"), results.getString("Country"));
    }

    /**
     * Gets a single Country from the database.
     * @param id ID of the Country to retrieve.
     * @return Returns an Optional containing the Country data retrieved from database.
     */
    @Override
    public Optional<Country> get(int id) {
        try (Connection connection = DBConnection.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Country_ID, Country FROM countries WHERE Country_ID=" + id);

            if(results.next()) {
                return Optional.of(extractFromResults(results));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Gets all the Countries listed in the Database.
     * @return An ObservableList containing all Countries retrieved from the database.
     */
    @Override
    public ObservableList<Country> getAll() {
        try(Connection connection = DBConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT Country_ID, Country FROM countries");

            ObservableList<Country> countries = FXCollections.observableArrayList();

            while(results.next()) {
                Country country = extractFromResults(results);
                countries.add(country);
            }

            return countries;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return FXCollections.observableArrayList();
    }

    /**
     * Inserts a Country into the Database.
     * This feature is not implemented at this time.
     * @param country Country to insert into database.
     * @return True if Country was successfully inserted, false otherwise.
     */
    @Override
    public boolean insert(Country country) {
        return false;
    }

    /**
     * Updates a Country in the Database.
     * This feature is not implemented at this time.
     * @param country Country to update in database.
     * @return True if Country was successfully updated, false otherwise.
     */
    @Override
    public boolean update(Country country) {
        return false;
    }

    /**
     * Deletes a Country in the Database.
     * This feature is not implemented at this time.
     * @param id ID of Country to delete from a database.
     * @return True if Country was successfully deleted, false otherwise.
     */
    @Override
    public boolean delete(int id) {
        return false;
    }
}