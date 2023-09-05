package Models;

/**
 * Contact Model.
 */
public class Contact {
    /** ID of the contact. */
    private int id;
    /** Name of the contact. */
    private String name;
    /** Email Address of the Contact. */
    private String email;

    /**
     * Default Constructor for contact.
     */
    public Contact() {
        this.id = -1;
        this.name = null;
        this.email = null;
    }

    /**
     * Parameterized constructor.
     * @param id ID of the contact.
     * @param name Name of the contact.
     * @param email Email address of the contact.
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Retrieves the ID of the contact.
     * @return ID of the contact.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the contact.
     * @param id ID of the contact.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the contact.
     * @return Name of the contact.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the contact.
     * @param name Name of the contact.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the email of the contact.
     * @return Email address of the contact.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the contact.
     * @param email Email of the contact.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}