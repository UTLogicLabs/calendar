package Controllers;

import Models.Appointment;
import Models.Customer;
import Models.User;
import Utilities.Alerts;
import Utilities.Database.AppointmentDao;
import Utilities.Database.CustomerDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calendar Controller.
 */
public class Calendar {

    /** DateTimeFormatter to format the dates to MM/dd/yyyy. */
    private final static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    /** DateTimeFormatter to format Date & Time to MM/dd/yyyy h:mm a z */
    private final static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a z");
    /** Creates a Logger object for this class. */
    private final static Logger LOGGER = Logger.getLogger(Calendar.class.getName());
    /** List of Customers retrieved from database. */
    private final ObservableList<Customer> customers = FXCollections.observableArrayList();
    /** List of Appointments retrieved from database. */
    private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    /** A filtered list of appointments. */
    private final FilteredList<Appointment> filteredAppointments = new FilteredList<>(appointments);
    /** Customer DAO object to manipulate the customer table in the database. */
    private final CustomerDao customerDao = new CustomerDao();
    /** Appointment DAO object to manipulate the Appointment table in the database. */
    private final AppointmentDao appointmentDao = new AppointmentDao();
    /** Current User that is logged into Calendar Application.*/
    private User user;

    @FXML
    private Label usernameLbl;

    @FXML
    private ToggleGroup CalendarDuration;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, Integer> appointmentId;

    @FXML
    private TableColumn<Appointment, String> appointmentTitle;

    @FXML
    private TableColumn<Appointment, String> appointmentDescription;

    @FXML
    private TableColumn<Appointment, String> appointmentLocation;

    @FXML
    private TableColumn<Appointment, String> appointmentContact;

    @FXML
    private TableColumn<Appointment, String> appointmentType;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentStartDateTime;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentEndDateTime;

    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerId;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> customerIdTableColumn;

    @FXML
    private TableColumn<Customer, String> customerNameTableColumn;

    @FXML
    private TableColumn<Customer, String> customerAddressTableColumn;

    @FXML
    private TableColumn<Customer, String> divisionNameTableColumn;

    @FXML
    private TableColumn<Customer, String> countryTableColumn;

    @FXML
    private TableColumn<Customer, String> postalCodeTableColumn;

    @FXML
    private TableColumn<Customer, String> phoneNumberTableColumn;

    @FXML
    private Label currentDateLbl;

    /**
     * Adds Appointments to the Appointment table on screen and in the database.
     * Uses a Lambda function in the consumer, that is passed to the
     * AppointmentDetails Controller. (Justification for Lambda ???)
     */
    @FXML
    private Button reportsButton;

    @FXML
    void showReports() throws IOException {
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/Reports.fxml")));
        newStage.setScene(new Scene(root));
        newStage.setResizable(false);
        newStage.setTitle(MESSAGES.getString("Title") + " : Reports");
        newStage.initStyle(StageStyle.DECORATED);
        newStage.show();
    }

    @FXML
    void addAppointment() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Views/AppointmentDetails.fxml"));
        Parent newRoot = loader.load();

        AppointmentDetails controller = loader.getController();
        Consumer<Appointment> onComplete = result -> {
            if(isConflict(result)){
                Alerts.information("Unable to create appointment.\nCustomer has a scheduling conflict.\nPlease try rescheduling.");
                System.out.println("Appointment has a conflict canceling appointment creation.");
                return;
            }

            result.setCreatedBy(user.getUsername());
            result.setLastUpdateBy(user.getUsername());
            result.setUserId(user.getId());
            result.setUser(user.getUsername());
            if(appointmentDao.insert(result)) {
                appointments.add(result);
            }
        };

        controller.initializeData(null, customers, onComplete);

        Stage newStage = new Stage();
        newStage.setScene(new Scene(newRoot));
        newStage.setTitle("Calendar: Add Appointment");
        newStage.setResizable(false);
        newStage.initStyle(StageStyle.DECORATED);

        newStage.show();
    }

    /**
     * Adds Customers to the Customer table on screen and in the database.
     * Uses a Lambda function in the consumer, that is passed to the
     * AppointmentDetails Controller. (Justification for Lambda ???)
     */
    @FXML
    void addCustomer() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Views/CustomerDetails.fxml"));
        Parent newRoot = loader.load();

        CustomerDetails controller = loader.getController();
        Consumer<Customer> onComplete = result -> {
            result.setLastUpdatedBy(user.getUsername());

            if(result.getCreatedBy() == null)
                result.setCreatedBy(user.getUsername());

            if(customerDao.insert(result))
                customers.add(result);
        };

        controller.initializeData(null, onComplete);

        Stage newStage = new Stage();
        newStage.setScene(new Scene(newRoot));
        newStage.setTitle("Calendar: Add Customer");
        newStage.setResizable(false);
        newStage.initStyle(StageStyle.DECORATED);
        newStage.show();
    }

    /**
     * Deletes appointments from Tables on screen and in the database.
     * Verifies if user wants to delete appointment, if false no deletion
     * occurs.
     * Validates that an appointment has been selected, if no appointment
     * is selected alerts user and does nothing.
     * Attempts to delete appointment from database, if unable to alerts user, and returns.
     * If all checks pass, appointment is removed from on screen table.
     */
    @FXML
    void deleteAppointment() {
        if(!Alerts.confirmation(Alerts.ConfirmType.DELETE))
            return;

        Appointment appointment = appointmentTableView.getSelectionModel().getSelectedItem();

        if(appointment == null) {
            Alerts.information("Must select an appointment first.");
            return;
        }

        if(!appointmentDao.delete(appointment.getId())) {
            Alerts.error("Unable to delete Appointment.\nIf issue persists please contact support.");
            return;
        }

        appointments.remove(appointment);
    }

    /**
     * Deletes customer from Tables on screen and in the database.
     * Verifies if user wants to delete customer, if false no deletion
     * occurs.
     * Validates that a customer has been selected, if no customer
     * is selected alerts user and does nothing.
     * Validates if a customer has 1+ appointments, this is using a
     * lambda function.*
     * If it is able to delete all appointments with the Customer ID,
     * then removes them from the on screen table using a lambda function.*
     * Then attempt to delete customer from table, if success removes
     * customer from on screen table.
     * *The lambda functions are used for clarity and readability. They
     * check appointments customer id against the customer to be deleted.
     */
    @FXML
    void deleteCustomer() {
        if(!Alerts.confirmation(Alerts.ConfirmType.DELETE))
            return;

        Customer customer = customerTableView.getSelectionModel().getSelectedItem();

        if(customer == null) {
            Alerts.information("Must select a customer first");
            return;
        }

        if(appointments.stream().anyMatch(appointment -> appointment.getCustomerId() == customer.getId())) {
            if(!Alerts.customConfirmation("Delete All Appointments?", "This customer still has appointments.\nDeleting customer will also delete all appointments. Continue?"))
                return;
            if(!appointmentDao.deleteAll(customer.getId())) {
                Alerts.error("Unable to delete Customer.\nIf issue persists please contact support.");
                return;
            }
            appointments.removeIf(appointment -> appointment.getCustomerId() == customer.getId());
        }

        if(!customerDao.delete(customer.getId()))
            Alerts.error("Unable to delete Customer.\nIf issue persists please contact support.");

        customers.remove(customer);
    }

    /**
     * Switches the calendar duration.
     * Uses a Lambda function as the predicate to the filtered appointments
     * list.
     * @lambda Predicate to check each appointment to see if it is within either,
     * the current week or month depending on the RadioButton that is currently
     * selected (All, Week, Month). Used for readability and clarity.
     */
    @FXML
    void switchCalendarDuration() {
        RadioButton selected = (RadioButton) CalendarDuration.getSelectedToggle();

        filteredAppointments.setPredicate(appointment -> {
            if (selected.getText().equals("Week")) {
                return appointment.getStart().isAfter(LocalDate.now().atStartOfDay()) && appointment.getStart().isBefore(LocalDateTime.now().plusDays(7));
            }
            if (selected.getText().equals("Month")) {
                return appointment.getStart().isAfter(LocalDate.now().atStartOfDay()) && appointment.getStart().isBefore(LocalDateTime.now().plusMonths(1));
            }
            return true;
        });
    }

    /**
     * Initializes the AppointmentDetails view, with an appointment to be updated.
     * @lambda onComplete Used to pass back the result from the Appointment Details
     * Screen. Used for readability and clarity.
     */
    @FXML
     void updateAppointment() throws IOException {
        Appointment appointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if(appointment == null) {
            Alerts.information("Appointment must be selected.\nPlease select an appointment.");
            LOGGER.log(Level.INFO, "Appointment not selected. Appointment must be selected.");
            return;
        }
        Consumer<Appointment> onComplete = result -> {
            result.setCreatedBy(user.getUsername());
            result.setLastUpdateBy(user.getUsername());
            if(appointmentDao.update(result)) {
                appointments.set(appointments.indexOf(appointment), result);
            }
        };

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Views/AppointmentDetails.fxml"));
        Parent newRoot = loader.load();

        AppointmentDetails controller = loader.getController();
        controller.initializeData(appointment, customers, onComplete);

        Stage newStage = new Stage();
        newStage.setScene(new Scene(newRoot));
        newStage.setTitle("Calendar: Update Appointment");
        newStage.setResizable(false);
        newStage.initStyle(StageStyle.DECORATED);

        newStage.show();
    }

    /**
     * Initializes the CustomerDetails view, with a customer to be updated.
     * @lambda onComplete Used to pass back the result from the Customer Details
     * Screen. Used for readability and clarity.
     */
    @FXML
    void updateCustomer() throws IOException {
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        if(customer == null) {
            Alerts.information("Customer must be selected.\nPlease select to a Customer from the Customer Table.");
            LOGGER.log(Level.INFO, "Customer not selected. Customer must be selected.");
            return;
        }

        Consumer<Customer> onComplete = result -> {
            result.setLastUpdatedBy(user.getUsername());

            if(customerDao.update(result)){
                customers.set(customers.indexOf(customer), result);
            }
        };

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Views/CustomerDetails.fxml"));
        Parent newRoot = loader.load();
        CustomerDetails controller = loader.getController();

        controller.initializeData(customer, onComplete);

        Stage newStage = new Stage();
        newStage.setScene(new Scene(newRoot));
        newStage.setTitle("Calendar: Update Customer");
        newStage.setResizable(false);
        newStage.initStyle(StageStyle.DECORATED);

        newStage.show();
    }

    /**
     * Initializes the Calendar view.
     * Sets the Current Date Label, initializes the Appointment, and Customer tables.
     * Then sets the visibility of the Appointment Table to the default filter.
     */
    @FXML
    void initialize() {
        currentDateLbl.setText(LocalDate.now().format(dateFormat));

        initializeAppointmentTable();
        initializeCustomerTable();

        switchCalendarDuration();
    }

    /**
     * Initializes Data. Sets the current user of the application.
     * Loads Customers and Appointments.
     * Validates if there is an appointment with in the next 15 minutes.
     * @param user User which has successfully logged into the Calendar Application.
     */
    public void initializeData(User user) {
        this.user = user;
        usernameLbl.setText(this.user.getUsername());

        loadCustomers();
        loadAppointments();

        if(hasUpcomingAppointment()) {
            Alerts.information("You have a meeting coming up in the next 15 minutes.");
        }
    }

    /**
     * Checks to see if there is an upcoming appointment within the next 15 minutes.
     * @lambda filter Filters the Appointments, for User ID and current date.
     * @lambda anyMatch checks the filtered appointments against the current time plus 15 minutes.
     * @return {@code true} if there is an appointment within the next 15 minutes,
     * otherwise {@code false}.
     */
    private boolean hasUpcomingAppointment() {
        return appointments.stream()
                .filter(appointment -> appointment.getUserId() == user.getId() && appointment.getStart().toLocalDate().isEqual(LocalDate.now()))
                .anyMatch(appointment -> appointment.getStart().toLocalTime().isBefore(LocalTime.now().plusMinutes(15)));
    }

    /**
     * Initializes the Customer Table.
     */
    private void initializeCustomerTable() {
        customerTableView.setItems(customers);
        customerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeTableColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryTableColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
    }

    /**
     * Initializes the Appointments table.
     */
    private void initializeAppointmentTable() {
        appointmentTableView.setItems(filteredAppointments);
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartDateTime.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentStartDateTime.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Appointment, LocalDateTime> call(TableColumn<Appointment, LocalDateTime> appointmentLocalDateTimeTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.atZone(ZoneId.systemDefault()).format(dateTimeFormat));
                        }
                    }
                };
            }
        });

        appointmentEndDateTime.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentEndDateTime.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Appointment, LocalDateTime> call(TableColumn<Appointment, LocalDateTime> appointmentLocalDateTimeTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.atZone(ZoneId.systemDefault()).format(dateTimeFormat));
                        }
                    }
                };
            }
        });

        appointmentCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * Checks to see if the Appointment has a conflict with an existing appointment.
     * @lambda filters the appointments with results CustomerID, and
     * either the start or end Dates are the same as the results.
     * @lambda anyMatch checks against multiple factors to see if the times of the
     * filtered appointments are conflicts for the Appointment results.
     * @param result result from the Appointments Detail Screen.
     * @return {@code true} if there is an appointment that overlaps start or end times.,
     * otherwise {@code false}.
     */
    private boolean isConflict(Appointment result) {
        return appointments.stream()
                .filter(appointment -> appointment.getCustomerId() == result.getCustomerId() && (
                        appointment.getStart().toLocalDate().equals(result.getStart().toLocalDate()) ||
                                appointment.getEnd().toLocalDate().equals(result.getEnd().toLocalDate())
                        )
                )
                .anyMatch(
                        appointment -> appointment.getStart().isEqual(result.getStart())
                                || appointment.getEnd().isEqual(result.getEnd())
                                || appointment.getStart().isBefore(result.getStart())
                                || appointment.getEnd().isBefore(result.getEnd())
                );
    }

    /**
     * Loads Customers from the database.
     */
    private void loadCustomers() {
        ObservableList<Customer> customerList = customerDao.getAll();
        customers.addAll(customerList);
    }

    /**
     * Loads Appointments from the database.
     */
    private void loadAppointments() {
        ObservableList<Appointment> appointmentList = appointmentDao.getAll();
        appointments.addAll(appointmentList);
    }
}