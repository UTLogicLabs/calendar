package Models;

/**
 * Model for Countries.
 */
public class Country {
    /** ID of the Country. */
    private int id;
    /** Name of country. */
    private String name;

    /**
     * Default constructor.
     */
    public Country() {
        this.id = -1;
        this.name = null;
    }

    /**
     * Parametrized constructor.
     * @param id ID of the Country.
     * @param name Name of the Country.
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Retrieves the ID of the country.
     * @return ID of the country.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the country.
     * @param id ID of the country.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the country.
     * @return Name of the country.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the country.
     * @param name Name of the country.
     */
    public void setName(String name) {
        this.name = name;
    }
}