package Utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Alert Class to handle all alerts
 */
public class Alerts {
    /**
     * CURRENTLOCALE retrieves Locale for internationalization
     */
    private final static Locale CURRENTLOCALE = Locale.getDefault();
    /**
     * MESSAGES is the Resource Bundle for internationalization
     */
    private final static ResourceBundle MESSAGES = ResourceBundle.getBundle("Resource/MessageBundle", CURRENTLOCALE);

    /**
     * Local Variable to hold alerts
     */
    private static Alert alert;

    /**
     * Confimation types EXIT, CANCEL, or Delete
     */
    public enum ConfirmType {EXIT, CANCEL, DELETE}

    /**
     * Creates an alert to get user to confirm an action
     * @param confirmType is type of action to confirm
     * @return boolean
     */
    public static boolean confirmation(ConfirmType confirmType) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        String title = "confirmTitle" + confirmType;
        String message = "confirmMessage" + confirmType;

        alert.setHeaderText(MESSAGES.getString(title));
        alert.setContentText(MESSAGES.getString(message));
        Optional<ButtonType> result = alert.showAndWait();

        return (result.isPresent() && result.get() == ButtonType.OK);
    }

    /**
     * Creates a custom alert to get user to confirm a custom action
     * @param title Title of alert
     * @param message Message to display to user
     * @return confirmation of action
     */
    public static boolean customConfirmation(String title, String message) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();

        return (result.isPresent() && result.get() == ButtonType.OK);
    }

    /**
     * Alert to let user know of an error.
     * @param errorMessage Message to display to user.
     */
    public static void error(String errorMessage) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(MESSAGES.getString("errorTitle"));
        alert.setContentText(errorMessage);
        alert.show();
    }

    /**
     * Alerts user with information
     * @param infoMessage message to display to user
     */
    public static void information(String infoMessage) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(infoMessage);
        alert.show();
    }
}