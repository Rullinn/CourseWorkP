package org.example.courseworkp;

import java.io.IOException;
import java.util.logging.*;

public class TourLogger {
    private static final Logger logger = Logger.getLogger(TourLogger.class.getName());

    public TourLogger() {
        try {
            // Створення файлового і опрацювання
            FileHandler fileHandler = new FileHandler("tour_system.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);


            logger.setLevel(Level.ALL);


            logger.addHandler(new EmailHandler());

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not setup logger", e);
        }
    }

    public void logInfo(String message) {
        logger.log(Level.INFO, message);
    }

    public void logWarning(String message) {
        logger.log(Level.WARNING, message);
    }

    public void logError(String message, Throwable thrown) {
        logger.log(Level.SEVERE, message, thrown);
    }
}

class EmailHandler extends Handler {
    @Override
    public void publish(LogRecord record) {
        if (record.getLevel() == Level.SEVERE) {
            // Реалізація створення email
            System.out.println("Sending email for critical error: " + record.getMessage());
        }
    }

    @Override
    public void flush() {}
    @Override
    public void close() throws SecurityException {}
}