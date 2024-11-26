import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class App {
    // Create a logger instance for the App class using Log4j 2.x
    final static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        // Call the method to log messages with different log levels
        logMeLikeYouDo("☕");
    }

    private static void logMeLikeYouDo(String input) {
        // Check if debug level is enabled, and log the message
        if (logger.isDebugEnabled()) {
            logger.debug("This is debug: " + input);
        }

        // Check if info level is enabled, and log the message
        if (logger.isInfoEnabled()) {
            logger.info("This is info: " + input);
        }

        // Log the message at the warn level (always logged)
        logger.warn("This is warn: " + input);

        // Log the message at the error level (always logged)
        logger.error("This is error: " + input);

        // Log the message at the fatal level (always logged)
        logger.fatal("This is fatal: " + input);
    }
}
