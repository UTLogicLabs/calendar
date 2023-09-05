package Controllers;

import Models.Appointment;
import Models.Contact;
import Models.Customer;
import Utilities.Alerts;
import Utilities.Database.ContactDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

/**
 * Appointment Details Screen.
 * Creates/Updates Appointments.
 */
public class AppointmentDetails {

    /** Appointment to be Created or updated */
    private Appointment appointment = null;

    /** Consumer to pass appointment back to the calling controller. */
    private Consumer<Appointment> onComplete;

    /** ObservableList to hold all Customers from the database. */
    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    /** ObservableList to hold all contacts from the database. */
    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();

    /** ObservableList to hold all start times. */
    private final ObservableList<LocalTime> startTimeList = FXCollections.observableArrayList();

    /** ObservableList to hold all end times. */
    private final ObservableList<LocalTime> endTimeList = FXCollections.observableArrayList();

    /** Time formatter to standard time format. */
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

    @FXML
    private TextField appointmentTitleTextField;

    @FXML
    private TextField appointmentLocationTextField;

    @FXML
    private TextField appointmentTypeTextField;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<LocalTime> startTimePicker;

    @FXML
    private ComboBox<LocalTime> endTimePicker;

    @FXML
    private TextArea appointmentDescriptionTextArea;

    @FXML
    private Button submitAppointment;

    @FXML
    private Button cancelAppointment;

    /**
     * Initializes the Appointment Details Screen.
     * Initializes the Date & Time Pickers, and ComboBoxes.
     */
    @FXML
    void initialize() {
        initializeDatePickers();
        initializeComboBoxes();
        startTimePickerInit();
        endTimePickerInit();
    }

    /**
     * Intializes the Datepickers.
     */
    private void initializeDatePickers() {
        startDatePicker.setValue(datePickerInitialDate());
        startDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY);
            }
        });
        startDatePicker.setEditable(false);
        endDatePicker.setValue(datePickerInitialDate());
        endDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY);
            }
        });
        endDatePicker.setEditable(false);
    }

    /**
     * Initializes the Contact ComboBox & Customer ComboBox.
     */
    private void initializeComboBoxes() {
        contactComboBox.setItems(contacts);
        contactComboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Contact> call(ListView<Contact> contactListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Contact item, boolean empty) {
                        super.updateItem(item, empty);

                        if(item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });
        contactComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Contact item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        customerComboBox.setItems(customers);
        customerComboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Customer> call(ListView<Customer> contactListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Customer item, boolean empty) {
                        super.updateItem(item, empty);

                        if(item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });
        customerComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

    /**
     * Checks the current time, if after
     * 22:00 then returns the next day.
     * @return LocalDate of either today or the next.
     */
    private LocalDate datePickerInitialDate() {
        if(LocalTime.now().isAfter(LocalTime.of(22, 0)))
            switch (LocalDate.now().plusDays(1).getDayOfWeek()){
                case SATURDAY:
                    return LocalDate.now().plusDays(3);
                case SUNDAY:
                    return LocalDate.now().plusDays(2);
                default:
                    return LocalDate.now().plusDays(1);
            }
        return LocalDate.now();

    }

    /**
     * Initializes the Start Time ComboBox.
     */
    private void startTimePickerInit() {
        startTimePicker.setItems(startTimeList);
        startTimePicker.setCellFactory(new Callback<>() {
            @Override
            public ListCell<LocalTime> call(ListView<LocalTime> stringListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(LocalTime item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null) {
                            setText(null);
                        } else {
                            setText(item.format(timeFormatter));
                        }
                    }
                };
            }
        });
        startTimePicker.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(LocalTime item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null || empty) {
                    setText(null);
                } else {
                    setText(item.format(timeFormatter));
                }
            }
        });
    }

    /**
     * Initializes the End Time ComboBox.
     */
    private void endTimePickerInit() {
        endTimePicker.setItems(endTimeList);
        endTimePicker.setCellFactory(new Callback<>() {
            @Override
            public ListCell<LocalTime> call(ListView<LocalTime> stringListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(LocalTime item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null) {
                            setText(null);
                        } else {
                            setText(item.format(timeFormatter));
                        }
                    }
                };
            }
        });
        endTimePicker.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(LocalTime item, boolean empty) {
                super.updateItem(item, empty);

                if(item == null || empty) {
                    setText(null);
                } else {
                    setText(item.format(timeFormatter));
                }
            }
        });
    }

    /**
     * Initializes Data coming from the Calendar Controller.
     * @param appointment Appointment to be updated, if present.
     * @param customers List of customers to add to appointment.
     * @param onComplete Consumer to return the new/updated appointment.
     */
    public void initializeData(Appointment appointment, ObservableList<Customer> customers, Consumer<Appointment> onComplete){
        this.onComplete = onComplete;
        this.customers.addAll(customers);

        loadContacts();
        loadStartEndTimes();
        setNewAppointmentTimes();
        loadAppointment(appointment);
    }

    /**
     * Retrieves Contacts from Database,
     */
    private void loadContacts() {
        ContactDao contactDao = new ContactDao();
        contacts.addAll(contactDao.getAll());
    }

    /**
     * Creates start and end times in EST then converts back to System Default Time.
     */
    private void loadStartEndTimes() {
        int[] quarterHours =  {0, 15, 30, 45, 0};

        for(int i = 8; i < 22; i++){
            for(int j = 0; j < 4; j++) {
                startTimeList.add(LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.of(i, quarterHours[j])
                ).atZone(ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.systemDefault()).toLocalTime());
                endTimeList.add(LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.of(i, quarterHours[j])
                ).atZone(ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.systemDefault()).toLocalTime());
            }
        }
        endTimeList.add(LocalDateTime.of(
                LocalDate.now(),
                LocalTime.of(22,0)
        ).atZone(ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.systemDefault()).toLocalTime());
    }

    /**
     * Sets the initial appointment times to the next quarter increment.
     * Maybe overridden by an incoming appointment to be updated.
     */
    private void setNewAppointmentTimes() {
        LocalTime current = LocalTime.now();
        startTimePicker.getSelectionModel().select(0);
        endTimePicker.getSelectionModel().select(1);
        for (LocalTime time: startTimeList){
            if(current.isBefore(time)){
                startTimePicker.getSelectionModel().select(time);
                endTimePicker.getSelectionModel().select(endTimeList.indexOf(time.plusMinutes(15)));
                break;
            }
        }
    }

    /**
     * Loads the appointment passed in from the Calendar Controller.
     */
    private void loadAppointment(Appointment appointment) {
        if(appointment == null) return;
        // Set Current Details
        appointmentTitleTextField.setText(appointment.getTitle());
        appointmentLocationTextField.setText(appointment.getDescription());
        appointmentTypeTextField.setText(appointment.getType());
        appointmentDescriptionTextArea.setText(appointment.getDescription());

        // Set Current Attendees
         contactComboBox.getSelectionModel().select(contacts.stream().filter(contact -> contact.getId() == appointment.getContactId()).findAny().orElse(null));
         customerComboBox.getSelectionModel().select(customers.stream().filter(customer -> customer.getId() == appointment.getCustomerId()).findAny().orElse(null));

        // Set current Date/Time Selectors
        // Start
        startDatePicker.setValue(appointment.getStart().toLocalDate());
        startTimePicker.getSelectionModel().select(appointment.getStart().toLocalTime());

        // End
        endDatePicker.setValue(appointment.getEnd().toLocalDate());
        endTimePicker.getSelectionModel().select(appointment.getEnd().toLocalTime());
    }

    /**
     * Checks to see if the user wants to cancel the current action.
     * If true closes current screen, otherwise returns to screen.
     */
    @FXML
    void cancel() {
        if(Alerts.confirmation(Alerts.ConfirmType.CANCEL)) {
            Stage stage = (Stage) cancelAppointment.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Passes back the appointment, to Calendar controller to be add-
     * ed to the Database, then closes the window.
     */
    @FXML
    void submit() {
        onComplete.accept(extractAppointmentDetails());

        Stage stage = (Stage) submitAppointment.getScene().getWindow();
        stage.close();
    }

    /**
     * Extracts the appointment details from the window,
     * and either updates the Existing Appointment or creates a new Appointment Object.
     * @return Appointment Object to be added to the calendar.
     */
    private Appointment extractAppointmentDetails() {
        if(appointment == null) appointment = new Appointment();

        // Set Appointment Details
        appointment.setTitle(appointmentTitleTextField.getText());
        appointment.setDescription(appointmentDescriptionTextArea.getText());
        appointment.setLocation(appointmentLocationTextField.getText());
        appointment.setType(appointmentTypeTextField.getText());

        // Set Appointment Times
        appointment.setStart(LocalDateTime.of(startDatePicker.getValue(), startTimePicker.getSelectionModel().getSelectedItem()));
        appointment.setEnd(LocalDateTime.of(endDatePicker.getValue(), endTimePicker.getSelectionModel().getSelectedItem()));

        // Set Attendees
        appointment.setCustomerId(customerComboBox.getSelectionModel().getSelectedItem().getId());
        appointment.setCustomer(customerComboBox.getSelectionModel().getSelectedItem().getName());
        appointment.setContactId(contactComboBox.getSelectionModel().getSelectedItem().getId());
        appointment.setContact(contactComboBox.getSelectionModel().getSelectedItem().getName());

        // Set Appointment Creation and Updated values.
        if(appointment.getCreated() == null)
            appointment.setCreated(LocalDateTime.now());
        appointment.setLastUpdate(LocalDateTime.now());

        return appointment;
    }
}
