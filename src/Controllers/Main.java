package Controllers;

import Utilities.Database.DBConnection;
import Utilities.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

/**
 * Main class to startup the Calendar Application
 */
public class Main extends Application {
    /**
     * Starts the Calendar Application.
     * @param primaryStage primary stage to start the Application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Logger.initializeLogManager();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/Login.fxml")));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }

    /**
     * Main method to start the application. Closes the connection to the database
     * if it is not already closed.
     * @param args Commandline arguments
     */
    public static void main(String[] args) {
        launch(args);
        DBConnection.closeConnection();
    }
}