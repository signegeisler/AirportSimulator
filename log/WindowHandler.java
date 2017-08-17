package log;

import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * From: http://www.java2s.com/Code/Java/Language-Basics/
 * JavalogLogandWindowJFrameframe.htm
 */
class WindowHandler extends Handler {
	private LogWindow window = null;
	private static WindowHandler handler = null;

	/**
	 * private constructor, preventing initialization
	 */
	private WindowHandler() {
		configure();
		if (window == null){
			window = new LogWindow("Logging window", 700, 1000);
		}
	}

	/**
	 * Returns the singleton instance of the WindowHandler object. Synchronized
	 * to prevent two threads trying to create an instance simultaneously.
	 * 
	 * @return WindowHandler object
	 */
	public static synchronized WindowHandler getInstance() {

		if (handler == null) {
			handler = new WindowHandler();
		}
		return handler;
	}

	/**
	 * Sets formatter properties.
	 */
	private void configure() {
		setLevel(Level.INFO);
		setFormatter(new SimpleFormatter());

	}

	/**
	 * Writes the logging information to the associated Java window. 
	 * This method is synchronized to make it thread-safe. In case 
	 * there is a problem, reports the problem with the ErrorManager, 
	 * only once and silently ignores the others.
	 * 
	 * @record the LogRecord object
	 * 
	 */
	public synchronized void publish(LogRecord record) {
		String message = null;
		// check if the record is loggable
		if (!isLoggable(record))
			return;
		try {
			message = record.getMessage();
		} catch (Exception e) {
			reportError(null, e, ErrorManager.FORMAT_FAILURE);
		}

		try {
			window.showInfo(message);
		} catch (Exception ex) {
			reportError(null, ex, ErrorManager.WRITE_FAILURE);
		}
	}

	public void close() {
	}

	public void flush() {
	}
}