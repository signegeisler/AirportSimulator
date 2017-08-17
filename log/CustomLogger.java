package log;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class CustomLogger {
	private WindowHandler handler;
	private Logger logger;

	public CustomLogger() {
		handler = WindowHandler.getInstance();
		logger = Logger.getLogger("sam.logging.handler");
		logger.addHandler(handler);
	}

	public void log(String message) {
		handler.publish(new LogRecord(Level.INFO, message + "\n"));
	}
}