package util;

/**
 *
 * Contains constants used throughout the application.
 *
 */
public class Constants {
	
	public static final int AIRPORT_PORT = 1346;
	public static final int AIRPLANE_SIMULATOR_PORT = 8890;
	public static final String AIRPORT_NAME = "Airport";
	public static final String AIRPLANE_SIMULATOR_NAME = "Airplane Simulator";
	public static final String RUNWAYS_LOCK = "runwayslock";
	public static final String REQUEST_QUEUE_LOCK = "requestqueuelock";
	public static final String QUEUE_HEAD = "head";
	public static final String QUEUE_TAIL = "tail";
	public static final String QUEUE_ELEMENT = "element";
	public static final String QUEUE_LOCK = "queuelock";
	public static final int MIN_SPAN_TIME = 5;
	public static final int MAX_SPAN_TIME = 10;
	public static final int MINUTES_IN_DAY = 1440;
	public static final int ALLOWED_POSTPONE_HOURS = 4;
	public static final String TAB = String.format("%c", '\t');
	
}