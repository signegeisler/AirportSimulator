package util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import entities.Airplane;
import enums.AirplaneSize;
import enums.AirplaneType;

public class AirplaneGenerator {
	public static ArrayList<Integer> flightList = new ArrayList<Integer>();
	static Util util = new Util();
	
	public static Airplane createAirplane(Calendar time){
		Airplane newAirplane = new Airplane(getUniqueFlightID(), getRandType(), getRandSize(), getRequestedTimeSpan(time));
		return newAirplane;
	}

	/**
	 * Creates a unique flight ID.
	 * @return a unique flight ID
	 */
	private static String getUniqueFlightID() {
		while (true) {
			int flightNumber = randomInteger(100, 1000);
			if (flightList.contains(flightNumber)) {
				continue;
			} else
				flightList.add(flightNumber);
			return "Flight " + flightNumber;
		}
	}

	/**
	 * Generates a random size for an airplane.
	 * @return	a random airplane size
	 */
	private static AirplaneSize getRandSize() {
		int size = randomInteger(1, 2);
		switch (size) {
		case 1:
			return AirplaneSize.SMALL;
		case 2:
			return AirplaneSize.BIG;
		default:
			return AirplaneSize.MEDIUM;
		}
	}

	/**
	 * Generates a random type for an airplane.
	 * @return	a random airplane type.
	 */
	private static AirplaneType getRandType() {
		int type = randomInteger(1, 2);
		if (type == 1) {
			return AirplaneType.CARGO;
		} else {
			return AirplaneType.PASSENGER;
		}
	}

	/**
	 * Generates a random integer in a given range.
	 * @param min	the minimum number possibly generated
	 * @param max	the maximum number possibly generated	
	 * @return		a random number between (and including)
	 * 				the given minimum and maximum
	 */
	private static int randomInteger(int min, int max) {
		Random randomInteger = new Random();
		int randomNumber = randomInteger.nextInt(max - min + 1) + min;
		return randomNumber;
	}

	/**
	 * Creates a time span for the request based on the current
	 * time of the application. 
	 * @param time	the current time of the application
	 * @return		a {@link TimeSpan} object with start and 
	 * 				end time or null if no time span could be 
	 * 				created
	 */
	private static TimeSpan getRequestedTimeSpan(Calendar time) {
		int randomStartOffset = randomInteger(15, 120);
		Calendar start = createOffsetTime(time, randomStartOffset);
		
		int minuteDif = Constants.MINUTES_IN_DAY - util.getDuration(util.createTime(0, 0), start);

		int randomDurationOffset = createDurationOffset(minuteDif);
		if(randomDurationOffset < 0){
			return null;
		}
		Calendar end = createOffsetTime(time, randomStartOffset + randomDurationOffset);

		TimeSpan requestedTimeSpan = new TimeSpan(start, end);
		return requestedTimeSpan;
	}

	/**
	 * Creates a random duration for the time span. 
	 * @param minuteDif	the difference in minutes between start time 
	 * 					and midnight
	 * @return			a randomly generated duration for the time 
	 * 					span that always results in the time span ending
	 * 					before midnight, or -1 if such a duration does
	 * 					not exist within the limitations 
	 */
	private static int createDurationOffset(int minuteDif) {
		int randomDurationOffset = 0;
		if (minuteDif <= Constants.MIN_SPAN_TIME) {
			return -1; // Fail
		} else if (minuteDif < Constants.MAX_SPAN_TIME + 1) {
			randomDurationOffset = randomInteger(Constants.MIN_SPAN_TIME, minuteDif - 1);
		} else {
			randomDurationOffset = randomInteger(Constants.MIN_SPAN_TIME, Constants.MAX_SPAN_TIME);
		}
		return randomDurationOffset;
	}

	/**
	 * Creates a new {@link Calendar} object based on the hour and 
	 * minute fields from another calendar and offsets it by a given
	 * number of minutes.
	 * @param time		the calendar to base the new calendar on
	 * @param offset	the number of minutes to offset the new 
	 * 					calendar by
	 * @return			a new calendar offset a given amount of 
	 * 					minutes from the given calendar
	 */
	private static Calendar createOffsetTime(Calendar time, int offset) {
		Calendar offsetTime = new GregorianCalendar();
		offsetTime.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
		offsetTime.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
		util.addDuration(offsetTime, offset);
		return offsetTime;
	}

	/**
	 * Removes a slight with a given ID from the flight list.
	 * @param flightID	the ID of the flight to remove
	 */
	public static void removeFlight(String flightID) {
		flightList.remove(flightID);
	}
}
