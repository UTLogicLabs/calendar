package Models;

import java.time.LocalDateTime;

/**
 * Appointment model.
 */
public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime created;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    private int customerId;
    private String customer;
    private int contactId;
    private String contact;
    private int userId;
    private String user;

    /**
     * Default constructor.
     */
    public Appointment() {
        this(-1, null, null, null, null, null, null, null, null, null,null,  -1, null, -1, null, -1, null);
    }

    /**
     * Paramatarized contstructor.
     * @param id ID of the Appointment.
     * @param title Title of the Appointment.
     * @param description Description of the appointment.
     * @param location Location of the appointment.
     * @param type Type of appointment.
     * @param start Start LocalDateTime of the Appointment.
     * @param end End LocalDateTime of the appointment.
     * @param created Date and Time the appointment was created.
     * @param createdBy User who Created the appointment.
     * @param lastUpdate Date and Time the appointment was last updated.
     * @param lastUpdateBy User who last updated the appointment.
     * @param customerId ID of Customer who is to attend appointment.
     * @param customer Customer Name of who is to attend the appointment
     * @param contactId ID of the Contact who is to attend the appointment.
     * @param contact Name of the contact who is to attend the appointment.
     * @param userId ID of the user who is supposed to attend the appointment.
     * @param user Username of user who is supposed to attend the appointment.
     */
    public Appointment(
            int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime created, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy,
            int customerId, String customer,
            int contactId, String contact,
            int userId, String user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.created = created;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.customerId = customerId;
        this.customer = customer;
        this.contactId = contactId;
        this.contact = contact;
        this.userId = userId;
        this.user = user;
    }

    /**
     * Retieves the ID of the appointment.
     * @return ID of the Appointment
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the Id of the appointment.
     * @param id ID of the appointment (Auto-Generated).
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Title of the appointment.
     * @return Title of the appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the appointment.
     * @param title Title of the appointment.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the description of the appointment.
     * @return Description of the appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the appointent.
     * @param description Description of the appointment.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the location for the appointment.
     * @return Location of the appointment.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location for the appointment.
     * @param location Location of the appointment.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Retrieves the type of the appointment.
     * @return Appointment Type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the appointment.
     * @param type Appointment Type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retrieves the start date and time of the appointment.
     * @return Start LocalDateTime of the appointment.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets the start time of the appointment.
     * @param start Start LocalDateTime of the appointment.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Retrieves the end date and time of the appointment.
     * @return Start LocalDateTime of the appointment.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets the end time of the appointment.
     * @param end Start LocalDateTime of the appointment.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Retrieves the Date and Time the appointment was created.
     * @return Date and Time that the appointment was created.
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Sets the Date and Time the appointment is created at.
     * @param created LocalDateTime appointment is created at.
     */
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * Retrieves the user who created the appointment.
     * @return Name of the user who created the appointment.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created the appointment.
     * @param createdBy Name of the user who created the appointment.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Retrieves the last time the appointment was updated.
     * @return Date and Time the appointment was updated.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the last time the appointment was updated.
     * @param lastUpdate Date and Time the appointment was updated.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Retrieves the user who last updated the appointment.
     * @return User who last updated the appointment.
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * Sets the user who last updated the appointment.
     * @param lastUpdateBy User who last updated the appointment.
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Retrieves the Customer ID who is to attend.
     * @return ID of Customer who is to attend.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the Customer ID who is to attend.
     * @param customerId ID of Customer who is to attend.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Retrieves the Customer name who is to attend.
     * @return Name of Customer who is to attend.
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * Sets the Customer name who is to attend.
     * @param customer Name of Customer who is to attend.
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     * Retrieves the contact ID who is to attend.
     * @return ID of contact who is to attend.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact ID who is to attend.
     * @param contactId ID of contact who is to attend.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Retrieves the contact name who is to attend.
     * @return Name of contact who is to attend.
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the contact name who is to attend.
     * @param contact Name of contact who is to attend.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Retrieves the user ID who is to attend.
     * @return ID of user who is to attend.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID who is to attend.
     * @param userId ID of user who is to attend.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the user name who is to attend.
     * @return Name of user who is to attend.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user name who is to attend.
     * @param user Name of user who is to attend.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Retrieves the customer ID who is to attend.
     * @return customerId ID of customer who is to attend.
     */
    public int getCustomerID() {
        return customerId;
    }
}
