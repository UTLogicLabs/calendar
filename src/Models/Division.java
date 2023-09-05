package Models;

/**
 * Division Model to hold Division Data
 */
public class Division {
    /** ID of Division. */
    private int id;
    /** Name of division. */
    private String name;
    private int countryId;

    /**
     * Constructor of Division.
     * @param id ID of Division
     * @param name Name of Division
     * @param countryId ID of Country that Division belongs to.
     */
    public Division(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    /**
     * Retrieves the ID of the division.
     * @return ID of division.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets Division ID.
     * @param id ID of Division.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves name of Division.
     * @return Division Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets division name.
     * @param name Name of Division.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retieves Country ID
     * @return ID of Country that the Division belongs to.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the Country ID.
     * @param countryId ID of Country.
     */
    public void setCountryID(int countryId) {
        this.countryId = countryId;
    }
}
