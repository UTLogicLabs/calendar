package Utilities.Database;


import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Optional;

public class CustomerDao implements DAO<Customer>{

    /**
     * Extracts Customer Data from results obtained from the database.
     * @param results Contains the data for a customer from the database.
     * @return Customer object built from database data.
     * @throws SQLException
     */
    private Customer extractFromResults(ResultSet results) throws SQLException {
        return new Customer(
            results.getInt("Customer_ID"),
            results.getString("Customer_Name"),
            results.getString("Address"),
            results.getString("Postal_Code"),
            results.getString("Phone"),
            results.getTimestamp("Create_Date").toLocalDateTime(),
            results.getString("Created_By"),
            results.getTimestamp("Last_Update").toLocalDateTime(),
            results.getString("Last_Updated_By"),
            results.getInt("Division_ID"),
            results.getString("Division"),
            results.getInt("Country_ID"),
            results.getString("Country")
        );
    }

    /**
     * Retrieves a single customer from the Database.
     * @param id ID of the Customer to retrieve from Database.
     * @return Returns an Optional containing the Customer.
     */
    @Override
    public Optional<Customer> get(int id) {
        try(Connection connection = DBConnection.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT customers.*, first_level_divisions.Division, first_level_divisions.COUNTRY_ID, countries.Country FROM customers, first_level_divisions, countries WHERE customers.Division_ID=first_level_divisions.Division_ID and first_level_divisions.COUNTRY_ID = countries.Country_ID and customer.Customer_ID=" + id);

            if(results.next()) {
                return Optional.of(extractFromResults(results));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Retrieves all Customers from the Database.
     * @return An ObservableList containing all Customers retrieved from the database.
     */
    @Override
    public ObservableList<Customer> getAll() {
        try (Connection connection = DBConnection.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT customers.*, first_level_divisions.Division, first_level_divisions.COUNTRY_ID, countries.Country FROM customers, first_level_divisions, countries WHERE customers.Division_ID=first_level_divisions.Division_ID and first_level_divisions.COUNTRY_ID = countries.Country_ID");

            ObservableList<Customer> customerList = FXCollections.observableArrayList();

            while(results.next()) {
                Customer customer = extractFromResults(results);
                customerList.add(customer);
            }

            return customerList;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return FXCollections.observableArrayList();
    }

    /**
     * Inserts a single Customer into database.
     * @param customer Customer Object to insert into database.
     * @return True if Country was successfully inserted, false otherwise.
     */
    @Override
    public boolean insert(Customer customer) {
        try(Connection connection = DBConnection.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO customers VALUE (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPostalCode());
            statement.setString(4, customer.getPhone());
            statement.setTimestamp(5, Timestamp.valueOf(customer.getCreated()));
            statement.setString(6, customer.getCreatedBy());
            statement.setTimestamp(7, Timestamp.valueOf(customer.getLastUpdated()));
            statement.setString(8, customer.getLastUpdatedBy());
            statement.setInt(9, customer.getDivisionId());

            int result = statement.executeUpdate();
            ResultSet results = statement.getGeneratedKeys();

            if (result == 1 & results.next()) {
                customer.setId(results.getInt(1));
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Updates a customer in the database.
     * @param customer Customer Data to update in Database
     * @return True if Country was successfully updated, false otherwise.
     */
    @Override
    public boolean update(Customer customer) {
        try(Connection connection = DBConnection.getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE customers SET Customer_Name=?,  Address=?, Division_ID=?, Postal_Code=?, Phone=?, Create_Date=?, Created_By=?, Last_Update=?, Last_Updated_By=? WHERE Customer_ID=?");

            statement.setString(1, customer.getName());
            statement.setString(2, customer.getAddress());
            statement.setInt(3, customer.getDivisionId());
            statement.setString(4, customer.getPostalCode());
            statement.setString(5, customer.getPhone());
            statement.setTimestamp(6, Timestamp.valueOf(customer.getCreated()));
            statement.setString(7, customer.getCreatedBy());
            statement.setTimestamp(8, Timestamp.valueOf(customer.getLastUpdated()));
            statement.setString(9, customer.getLastUpdatedBy());
            statement.setInt(10, customer.getId());

            int result = statement.executeUpdate();

            if(result == 1) return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes a single Customer from database.
     * @param id ID of Customer to delete from database.
     * @return True if Country was successfully deleted, false otherwise.
     */
    @Override
    public boolean delete(int id) {
        try(Connection connection = DBConnection.getConnection()){
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate("DELETE FROM customers WHERE Customer_ID=" + id);

            if(result == 1) return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}