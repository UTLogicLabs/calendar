package Models;

import java.time.LocalDateTime;

/**
 * Customer Model
 */
public class Customer {
    /** ID of Customer. */
    private int id;
    /** Name of Customer. */
    private String name;
    /** Address of Customer. */
    private String address;
    /** Postal Code of Customer. */
    private String postalCode;
    /** Phone number of Customer. */
    private String phone;
    /** Date and time Customer was created. */
    private LocalDateTime created;
    /** Who created the Customer. */
    private String createdBy;
    /** Last date and time the Customer was updated. */
    private LocalDateTime lastUpdated;
    /** Who last updated the Customer. */
    private String lastUpdatedBy;
    /** ID of Division where customer lives.  */
    private int divisionId;
    /** Division Name where customer lives. */
    private String division;
    /** ID of Country where customer lives */
    private int countryId;
    /** Name of Country where customer lives. */
    private String country;

    /**
     * Default constructor.
     */
    public Customer() {
        id = -1;
    }

    /**
     * Parameterized Constructor.
     * @param id Customer ID.
     * @param name Customer Name.
     * @param address Customer Address.
     * @param postalCode Customer Postal Code.
     * @param phone Customer Phone Number.
     * @param created Date Customer was created.
     * @param createdBy Who Created the Customer.
     * @param lastUpdated When the customer was last updated.
     * @param lastUpdatedBy Who updated the customer last.
     * @param divisionId The ID of the First Level Division customer lives in (State/Province).
     * @param division Name Of fist level division customer lives in (State/Province).
     * @param countryId ID of the Country the division/customer lives is in.
     * @param country Name of the Country the division/customer lives is in.
     */
    public Customer(int id, String name, String address, String postalCode, String phone, LocalDateTime created, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy, int divisionId, String division, int countryId, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.created = created;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * Retrieves ID of Customer.
     * @return ID of Customer.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets ID of customer.
     * @param id ID of the customer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves name of Customer.
     * @return Customer name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of Customer.
     * @param name Name of Customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves Customer address.
     * @return Address of Customer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets Customers Address.
     * @param address Address of customer.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves Postal Code of Customer.
     * @return Postal Code of customer.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets Postal Code of customer.
     * @param postalCode Postal Code of customer.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Retrieves Phone number of customer.
     * @return Phone Number of Customer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets Phone number of Customer.
     * @param phone Phone number of customer to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Retrieves the Date and Time that Customer was last updated.
     * @return The LocalDateTime the customer was last updated.
     */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Retrieves the last user to update the customer.
     * @return Name of the last user to update the customer.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Retrieves the date and time the customer was created.
     * @return LocalDateTime of the creation of the customer.
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Sets the Date Time the customer was created.
     * @param created The LocalDateTime the customer was created.
     */
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * Sets the name of the user who created the customer.
     * @param createdBy Name of the user who created the customer.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Retrieves the Username of the person who created the customer
     * @return Name of the User of who created the customer.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the LocalDateTime the customer was last updated.
     * @param lastUpdated LocalDateTime the customer was last updated.
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Sets the username who updated the customer last.
     * @param lastUpdatedBy Last user to update the customer.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Retrieves the Division ID for where the customer lives.
     * @return ID of the Division where the customer lives.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the Division ID of where the customer lives.
     * @param divisionId ID of the Division where customer lives.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Retrieves the Division name of where customer lives.
     * @return Name of the Division where the customer lives.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the division name of where the customer lives.
     * @param division Division name.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Retrieves the ID of the Country in which the customer resides.
     * @return ID of the country where the customer resides.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the Country ID where the cusotmer resides.
     * @param countryId ID of the country where Customer Resides.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Retrieves the name of the country were the customer lives.
     * @return Name of the country were the customer lives.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the country where the customer resides.
     * @param country Name of the country where the customer resides.
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
