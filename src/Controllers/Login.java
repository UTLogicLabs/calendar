package Controllers;

import Models.User;
import Utilities.Database.UserDao;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Login Screen Controller
 */
public class Login {

    /** Current Locale for internationalization. */
    private final static Locale CURRENT_LOCALE = Locale.getDefault();
    /** Logger for application. */
    private final static Logger LOGGER = Logger.getLogger(Calendar.class.getName());

    /** Resource Bundle for internationalization. */
    private final static ResourceBundle MESSAGES = ResourceBundle.getBundle("Resource/MessageBundle", CURRENT_LOCALE);

    @FXML
    private Text programTitle;

    @FXML
    private Label zoneIdLbl;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button closeBtn;

    @FXML
    private Text errorMessageTxt;

    /**
     * Closes the Application.
     */
    @FXML
    void CloseApplication() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Logs in user and switches to the Calendar view.
     * @throws IOException
     */
    @FXML
    void LoginUser() throws IOException {
        try {
            User user = getUser(usernameTextField.getText(), passwordTextField.getText());
            LOGGER.log(Level.INFO, user.getUsername() + " logged in successfully");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Views/Calendar.fxml"));
            Parent newRoot = loader.load();

            Calendar controller = loader.getController();
            controller.initializeData(user);

            Stage newStage = new Stage();
            newStage.setScene(new Scene(newRoot));
            newStage.setTitle("Calendar");
            newStage.setResizable(true);
            newStage.initStyle(StageStyle.DECORATED);
            newStage.setOnCloseRequest(windowEvent -> Platform.exit());

            Stage currentStage = (Stage) loginBtn.getScene().getWindow();
            currentStage.close();

            newStage.show();
        } catch (NoSuchElementException ex) {
            LOGGER.log(Level.INFO, usernameTextField.getText() + " attempted to login.");
            errorMessageTxt.setText(MESSAGES.getString("LoginError"));
        }

    }

    /**
     * Initializes the Login screen.
     * Event handlers use a Lambda Function to allow the user to press the
     * Enter KEY to attempt to log in to the application.
     */
    @FXML
    void initialize() {
        errorMessageTxt.setText("");
        programTitle.setText(MESSAGES.getString("Title"));
        usernameTextField.setPromptText(MESSAGES.getString("UsernamePrompt"));
        passwordTextField.setPromptText(MESSAGES.getString("PasswordPrompt"));
        zoneIdLbl.setText(ZoneId.systemDefault().toString());

        /** Allows a user press the enter key on the Username TextField to log in to application. */
        usernameTextField.addEventHandler(KeyEvent.KEY_PRESSED, ev-> {
            if(ev.getCode() == KeyCode.ENTER) {
                try{
                    LoginUser();
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        /** Allows a user press the enter key on the Password TextField to log in to application. */
        passwordTextField.addEventHandler(KeyEvent.KEY_PRESSED, ev-> {
            if(ev.getCode() == KeyCode.ENTER) {
                try{
                    LoginUser();
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        /** Allows a user press the enter key on the Login Button to log in to application. */
        loginBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev-> {
            if(ev.getCode() == KeyCode.ENTER) {
                try{
                    LoginUser();
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Retrieves an optional user from the database, and extracts the results from the optional.
     * @param username Username to search the database for.
     * @param password Password for the user to match in the database.
     * @return User who is now logged into the application.
     * @throws NoSuchElementException Thrown when the user is not found.
     */
    private User getUser(String username, String password) throws NoSuchElementException {
        UserDao userDao = new UserDao();
        Optional<User> user = userDao.getUserByUserNameAndPassword(username, password);

        return user.orElseThrow();
    }
}