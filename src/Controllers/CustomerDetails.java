package Controllers;

import Models.Country;
import Models.Customer;
import Models.Division;
import Utilities.Alerts;
import Utilities.Database.CountryDao;
import Utilities.Database.DivisionsDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.util.function.Consumer;

/**
 * Customer Detail Screen
 * Displays a Customers Details in order create/edit a Customer.
 */
public class CustomerDetails {

    /** Customer to create/edit. */
    private Customer customer = null;

    /** Consumer to be passed in from the calendar controller in order to pass back the Customer. */
    private Consumer<Customer> onComplete;

    /** List of all Countries from database. */
    private final ObservableList<Country> countries = FXCollections.observableArrayList();

    /** List of all First Level Divisions from database. */
    private final ObservableList<Division> divisions = FXCollections.observableArrayList();

    @FXML
    private Text customerDetailsTitle;

    @FXML
    private Label customerIdLbl;

    @FXML
    private TextField customerIdTextField;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField customerAddressTextField;

    @FXML
    private TextField phoneNumber;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private ComboBox<Division> stateProvinceComboBox;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private Button submitBtn;

    @FXML
    private Button cancelBtn;

    /**
     * Add Customer
     * Creates a new customer if needed, and set all customer
     * attributes.
     * Sends customer back to through the consumer to Calendar.
     * Then closes the Customer Details Window.
     */
    @FXML
    void addCustomer() {
        if (customer == null)
            customer = new Customer();

        customer.setName(customerNameTextField.getText());
        customer.setAddress(customerAddressTextField.getText());
        customer.setDivisionId(stateProvinceComboBox.getValue().getId());
        customer.setDivision(stateProvinceComboBox.getValue().getName());
        customer.setCountryId(countryComboBox.getValue().getId());
        customer.setCountry(countryComboBox.getValue().getName());
        customer.setPostalCode(postalCodeTextField.getText());
        customer.setPhone(phoneNumber.getText());
        if(customer.getCreated() == null)
            customer.setCreated(LocalDateTime.now());
        customer.setLastUpdated(LocalDateTime.now());

        onComplete.accept(customer);

        Stage stage = (Stage) submitBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Verifies that the customer wants to cancel the action
     * (Add/Updating a customer). If customer accepts, then
     * window is closed and no action is done.
     */
    @FXML
    void cancel()  {
        if(Alerts.confirmation(Alerts.ConfirmType.CANCEL)) {
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Initializes the Customer Details window.
     * Populates the stateProvinceComboBox with
     * all first Level Divisions from database,
     * and the countryComboBox with all countries
     * from the database.
     */
    @FXML
    void initialize() {

        stateProvinceComboBox.setItems(divisions);
        stateProvinceComboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Division> call(ListView<Division> divisionListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Division item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });
        stateProvinceComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Division item, boolean empty) {
                super.updateItem(item, empty);

                if(item != null) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });


        countryComboBox.setItems(countries);
        countryComboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Country> call(ListView<Country> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Country item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });
        countryComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Country item, boolean empty) {
                super.updateItem(item, empty);

                if(item != null) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
        countryComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int countryId = countryComboBox.getSelectionModel().getSelectedItem().getId();
                ObservableList<Division> filteredDivisions = FXCollections.observableArrayList();

                for (Division division : divisions) {
                    if (division.getCountryId() == countryId) {
                        filteredDivisions.add(division);
                    }
                }

                stateProvinceComboBox.setItems(filteredDivisions);
            }
        });
    }

    /**
     * Initializes data sent in from the calling controller.
     * Loads Countries, first level divisions and customer data.
     * @param customer Customer to update if not null.
     * @param onComplete Consumer to be able to send
     *                  created/Updated customer back to the
     *                  calling controller.
     */
    public void initializeData(Customer customer, Consumer<Customer> onComplete) {
        this.customer = customer;
        this.onComplete = onComplete;

        loadCountries();
        loadDivisions();
        loadCustomer();
    }

    /**
     * Loads Countries from the database and adds them to the
     * corresponding ObservableList.
     */
    private void loadCountries() {
        CountryDao countryDao = new CountryDao();
        ObservableList<Country> countryList = countryDao.getAll();
        countries.addAll(countryList);
    }

    /**
     * Loads First Level Divisions from the database and adds
     * them to the corresponding ObservableList.
     */
    private void loadDivisions() {
        DivisionsDao divisionDao = new DivisionsDao();
        ObservableList<Division> divisionList = divisionDao.getAll();
        divisions.addAll(divisionList);
    }

    /**
     * Loads customer data to the corresponding
     * TextFields and chooses the division and
     * country combobox options if customer is
     * to be updated.
     */
    private void loadCustomer() {
        if (customer != null ) {
            customerIdTextField.setText(String.valueOf(customer.getId()));
            customerNameTextField.setText(customer.getName());
            customerAddressTextField.setText(customer.getAddress());
            postalCodeTextField.setText(customer.getPostalCode());
            phoneNumber.setText(customer.getPhone());

            for (Division division: divisions) {
                if(division.getId() != customer.getDivisionId()) continue;
                stateProvinceComboBox.setValue(division);

                for(Country country:countries) {
                    if (country.getId() != division.getCountryId()) continue;
                    countryComboBox.setValue(country);
                }
            }
        }
    }
}