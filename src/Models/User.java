package Models;

/**
 * User Model to hold user data
 */
public class User {
    /**
     * ID of User.
     */
    private int id;
    /**
     * Username of user.
     */
    private String username;

    /**
     * Default Constructor.
     * Sets id to -1 and username to null.
     */
    public User() {
        this.id = -1;
        this.username = null;
    }

    /**
     * Parameterized Constructor
     * @param id ID of user.
     * @param username Username of user.
     */
    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * Retieves User ID.
     * @return ID of user.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets ID of user.
     * @param id ID to set of user.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves username of user.
     * @return Username of user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets Username of user.
     * @param username Username to set of user.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}