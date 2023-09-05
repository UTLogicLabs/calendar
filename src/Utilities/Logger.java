package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;

/**
 * Class to create Logger
 */
public class Logger {
    /**
     * Initializes Log Manager from logging.properties
     */
    public static void initializeLogManager() throws IOException {
        LogManager.getLogManager().readConfiguration(new FileInputStream("./src/Resource/logging.properties"));
    }
}
