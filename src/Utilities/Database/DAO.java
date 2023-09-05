package Utilities.Database;

import javafx.collections.ObservableList;
import java.util.Optional;

/**
 * Creates an Data Access Object interface of type T.
 * @param <T> Object for DAO implementation
 */
public interface DAO<T> {
    /**
     * Retrieves Single Object of type T.
     * @param id ID of type T to retrieve.
     * @return Returns an Optional of type T.
     */
    Optional<T> get(int id);

    /**
     * Retrieves all objects of type T.
     * @return ObservableList of type T.
     */
    ObservableList<T> getAll();

    /**
     * Inserts type T object into database.
     * @param t Object of type T to insert
     * @return True if Object was successfully inserted, false otherwise.
     */
    boolean insert(T t);

    /**
     * Updates type T object in database.
     * @param t Object of type T to update.
     * @return True if Object was successfully updated, false otherwise.
     */
    boolean update(T t);

    /**
     * Deletes single object of type T from database.
     * @param id ID of object of type T to delete.
     * @return True if Country was successfully deleted, false otherwise.
     */
    boolean delete(int id);
}
