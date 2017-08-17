package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import entities.Request;
import entities.Runway;
import entities.TimeResponse;
import interfaces.IScheduler;
import log.CustomLogger;
import util.Constants;
import util.TimeSpan;
import util.Util;

/**
 * 
 * Handles the scheduling of airplanes. 
 *
 */
public class ScheduleService implements IScheduler {

	CustomLogger logger = new CustomLogger();
	Util util = new Util();
	
	public ScheduleService() { }

	/**
	 * Schedules a {@link TimeSpan} on a runway for an airplane, 
	 * based on a {@link Request} object with a desired time. 
	 * If the exact requested time span is not available on any
	 * runway, the time span will be postponed to the closest 
	 * possible time.
	 * 
	 * @param request	the request containing the desired time
	 * @param runways	the runways which match the size of the 
	 * 					airplane
	 * @return			a new {@link TimeResponse} object with 
	 * 					the scheduled time span or null if the
	 * 					time span cannot be scheduled.
	 */
	public TimeResponse schedule(Request request, List<Runway> runways) {
		ArrayList<TimeSpan> schedule = new ArrayList<>();
		TimeSpan requestedTimeSpan = request.getTime();
		TimeResponse response = null;
		TimeSpan closestTimeSpan = null;

		for (Runway runway : runways) {
			schedule = runway.getSchedule();

			if (verifyRequest(requestedTimeSpan, schedule)) {
				schedule.add(requestedTimeSpan);
				return new TimeResponse(requestedTimeSpan, runway);
			} else {
				TimeSpan tempTimeSpan = findClosestTimeSpanIn(schedule, requestedTimeSpan);
				if (closestTimeSpan == null
						|| util.compareTo(tempTimeSpan.getStart(), closestTimeSpan.getStart()) < 0) {
					closestTimeSpan = tempTimeSpan;
				}
			}
			response = new TimeResponse(closestTimeSpan, runway);
		}
		if (response != null && closestTimeSpan != null) {
			response.getRunway().getSchedule().add(closestTimeSpan);
		} else {
			return null;
		}
		return response;
	}

	/**
	 * Determines whether a requested time span can be verified, i.e.
	 * placed directly in the schedule without overlapping already
	 * scheduled times. 
	 * @param requestedTimeSpan	the requested time span
	 * @param schedule			the schedule of occupied time spans
	 * @return					true if the requested time span can 
	 * 							be verified, otherwise false
	 */
	private boolean verifyRequest(TimeSpan requestedTimeSpan, List<TimeSpan> schedule) {
		for (TimeSpan timeSpan : schedule) {
			if (util.isOverlapping(requestedTimeSpan, timeSpan) || util.isOverlapping(timeSpan, requestedTimeSpan)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Finds the closest matching {@link TimeSpan} to the request in the given
	 * schedule. Assumes that the request time span does not overlap midnight.
	 * 
	 * @param schedule			the schedule for a runway
	 * @param requestedTimeSpan	the requested time span
	 * @return 					a {@link TimeSpan} object that starts after, 
	 * 							but as close as possible to the requested time 
	 * 							span or null if it is not possible to place
	 * 							the time span in the schedule.
	 */
	public TimeSpan findClosestTimeSpanIn(List<TimeSpan> schedule, TimeSpan requestedTimeSpan) {
				
		// Sort the schedule based on start times of the time spans
		Collections.sort(schedule);

		for (int i = 0; i < schedule.size(); i++) {
			TimeSpan currentTimeSpan = schedule.get(i);
			boolean isLastIteration = (i == schedule.size() - 1);

			// If currentTimeSpan ends after requestedTimeSpan starts
			// And the time span is postponed less than the allowed
			if(util.compareTo(currentTimeSpan.getEnd(), requestedTimeSpan.getStart()) >= 0 && 
					util.differenceIsLessThan(currentTimeSpan.getStart(), requestedTimeSpan.getStart(), Constants.ALLOWED_POSTPONE_HOURS)){
			
				TimeSpan returnTimeSpan = createPostponedTimeSpan(requestedTimeSpan, currentTimeSpan);

				if (isLastIteration) {
					if (!util.overlapsMidnight(returnTimeSpan)) {
						return returnTimeSpan;
					} else {
						return postpone(schedule, returnTimeSpan);
					}
				// If the next time span starts after the new end time
				} else if(!util.isOverlapping(schedule.get(i + 1), returnTimeSpan) && !util.isOverlapping(returnTimeSpan, schedule.get(i + 1))){
					return returnTimeSpan;
				}
			}
		}
		return null;
	}

	/**
	 * Handles postponing a {@link TimeSpan} object to just after midnight
	 * and checks if it can now be placed in the schedule, either directly
	 * at the postponed time, or if it must be further postponed.  
	 * @param schedule			the schedule for a runway
	 * @param timeSpan			the time span to be postponed. 
	 * @return					a new {@link TimeSpan} object which can be
	 * 							placed in the schedule or null if the 
	 * 							time span cannot be placed. 
	 */
	private TimeSpan postpone(List<TimeSpan> schedule, TimeSpan timeSpan) {
		TimeSpan postponed = moveToAfterMidnight(timeSpan);
		if(verifyRequest(postponed, schedule)){
			return postponed;
		} else {
			return findClosestTimeSpanIn(schedule, postponed);
		}
	}
	
	/**
	 * Moves a {@link TimeSpan} object's start time to just after midnight and
	 * also moves the end time accordingly. 
	 * @param timeSpan	the time span to be moved.  
	 * @return			a new {@link TimeSpan} object starting just after
	 * 					midnight. 
	 */
	private TimeSpan moveToAfterMidnight(TimeSpan timeSpan){
		Calendar start = new GregorianCalendar();
		Calendar end = new GregorianCalendar();
		start.set(Calendar.HOUR_OF_DAY, timeSpan.getStart().get(Calendar.HOUR_OF_DAY));
		start.set(Calendar.MINUTE, timeSpan.getStart().get(Calendar.MINUTE));
		end.set(Calendar.HOUR_OF_DAY, timeSpan.getEnd().get(Calendar.HOUR_OF_DAY));
		end.set(Calendar.MINUTE, timeSpan.getEnd().get(Calendar.MINUTE));
		
		int durationInMinutes = Constants.MINUTES_IN_DAY - util.getDuration(util.createTime(0, 0), timeSpan.getStart());
		util.addDuration(start, durationInMinutes);
		util.addDuration(end, durationInMinutes);
		
		return new TimeSpan(start, end);
	}

	/**
	 * Creates a new postponed {@link TimeSpan} based on the difference between
	 * the start of the requestedTimeSpan and the end of the currentTimeSpan.
	 * 
	 * @param requestedTimeSpan	the requested time span
	 * @param currentTimeSpan	the current time span 
	 * @return					a new {@link TimeSpan} object, postponed 
	 * 							sufficiently.
	 */
	private TimeSpan createPostponedTimeSpan(TimeSpan requestedTimeSpan, TimeSpan currentTimeSpan) {
		Calendar newEndTime = new GregorianCalendar();
		newEndTime.set(Calendar.HOUR_OF_DAY, requestedTimeSpan.getEnd().get(Calendar.HOUR_OF_DAY));
		newEndTime.set(Calendar.MINUTE, requestedTimeSpan.getEnd().get(Calendar.MINUTE));

		int durationInMinutes = util.getDuration(requestedTimeSpan.getStart(), currentTimeSpan.getEnd());
		newEndTime = util.addDuration(newEndTime, durationInMinutes);

		return new TimeSpan(currentTimeSpan.getEnd(), newEndTime);
	}
}
