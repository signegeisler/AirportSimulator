package util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 
 * Contains utility methods for handling time and time spans.
 *
 */
public class Util {
	
	public Util() { }

	/**
	 * Compares two {@link Calendar} objects based only on hour of day
	 * and minute. 
	 * @param calendarOne	the first calendar to compare	
	 * @param calendarTwo	the second calendar to compare
	 * @return				a negative number if calendarOne is before 
	 * 						calendarTwo, a positive number if calendarOne
	 * 						if after calendarTwo, otherwise zero.
	 */
	public int compareTo(Calendar calendarOne, Calendar calendarTwo) {
		if (calendarOne.get(Calendar.HOUR_OF_DAY) < calendarTwo.get(Calendar.HOUR_OF_DAY)) {
			return -1;
		} else if (calendarOne.get(Calendar.HOUR_OF_DAY) > calendarTwo.get(Calendar.HOUR_OF_DAY)) {
			return 1;
		} else {
			if (calendarOne.get(Calendar.MINUTE) < calendarTwo.get(Calendar.MINUTE)) {
				return -1;
			} else if (calendarOne.get(Calendar.MINUTE) > calendarTwo.get(Calendar.MINUTE)) {
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * Determines whether two {@link TimeSpan} objects are overlapping.
	 * @param firstTimeSpan		the first time span
	 * @param secondTimeSpan	the second time span
	 * @return					true if the two time spans overlap, 
	 * 							otherwise false.
	 */
	public boolean isOverlapping(TimeSpan firstTimeSpan, TimeSpan secondTimeSpan) {

		// If the two time spans are equal, they are overlapping
		if (compareTo(firstTimeSpan.getStart(), secondTimeSpan.getStart()) == 0
				&& compareTo(firstTimeSpan.getEnd(), secondTimeSpan.getEnd()) == 0) {
			return true;
		
		// Else if start time is after end time, we know the time span 
		// overlaps midnight and can handle this case
		} else if (compareTo(secondTimeSpan.getStart(), secondTimeSpan.getEnd()) > 0) {
			if (compareTo(firstTimeSpan.getStart(), secondTimeSpan.getEnd()) < 0
					|| compareTo(firstTimeSpan.getStart(), secondTimeSpan.getStart()) > 0
					|| compareTo(firstTimeSpan.getEnd(), secondTimeSpan.getEnd()) < 0
					|| compareTo(firstTimeSpan.getEnd(), secondTimeSpan.getStart()) > 0) {
				return true;
			}
		// Else we just handle the checking normally
		} else if (compareTo(firstTimeSpan.getStart(), secondTimeSpan.getStart()) > 0
				&& compareTo(firstTimeSpan.getStart(), secondTimeSpan.getEnd()) < 0
				|| compareTo(firstTimeSpan.getEnd(), secondTimeSpan.getStart()) > 0
						&& compareTo(firstTimeSpan.getEnd(), secondTimeSpan.getEnd()) < 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks directly whether a time span overlaps midnight. 
	 * @param timeSpan 	the time span to check.
	 * @return			true, if the time span overlaps 
	 * 					midnight, otherwise false.
	 */
	public boolean overlapsMidnight(TimeSpan timeSpan){
		if (compareTo(timeSpan.getStart(), timeSpan.getEnd()) > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a string representation of a {@link Calendar} object.
	 * @param cal	the calendar to create a string representation for
	 * @return		a {@link String} object representing the calendar
	 * 				in a readable format
	 */
	public String asString(Calendar cal){
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);

		if (min < 10) {
			return hour + ":0" + min;
		} else {
			return hour + ":" + min;
		}
	}
	
	/**
	 * Creates a new {@link Calendar} object with the fields for 
	 * hour and minute set to the given values.
	 * @param hour	the hour to set the hour field to
	 * @param min	the minute to set the minute field to
	 * @return		a new {@link Calendar} object with the fields
	 * 				set to the given values
	 */
	public Calendar createTime(int hour, int min) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		return cal;
	}
	
	/**
	 * Adds a given amount of minutes to a {@link Calendar} object.
	 * @param currentTime	the calendar to add minutes to
	 * @param duration		the amount of minutes to add 
	 * @return				a {@link Calendar} object with the given
	 * 						amount of minutes added
	 */
	public Calendar addDuration(Calendar currentTime, int duration){
		currentTime.add(Calendar.MINUTE, duration);
		if (currentTime.get(Calendar.DATE) != 1){
			currentTime.set(Calendar.DATE, 1);
		}
		return currentTime;
	}

	/**
	 * Calculates the difference in minutes between two {@link Calendar}
	 * objects. Assumes that end is after start.
	 * @param timeOne	the first time
	 * @param timeTwo	the second time
	 * @return			the difference in minutes between the first time 
	 * 					and the second time
	 */
	public int getDuration(Calendar timeOne, Calendar timeTwo) {

		int minuteDif = (timeTwo.get(Calendar.HOUR_OF_DAY) * 60 + timeTwo.get(Calendar.MINUTE))
				- (timeOne.get(Calendar.HOUR_OF_DAY) * 60 + timeOne.get(Calendar.MINUTE));

		return minuteDif;
	}

	/**
	 * Determines whether the difference between two {@link Calendar}
	 * objects is less than a given amount of hours.
	 * @param timeOne	the first time
	 * @param timeTwo	the second time
	 * @param hours		the amount of hours 
	 * @return			true if the difference in hours between the 
	 * 					given calendars is less than the given amount
	 * 					of hours, otherwise false
	 */
	public boolean differenceIsLessThan(Calendar timeOne, Calendar timeTwo, int hours) {
		int differenceInHours = (int) Math.ceil(getDuration(timeTwo, timeOne) / 60);
		if (differenceInHours < hours) {
			return true;
		}
		return false;
	}
}
