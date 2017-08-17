package entities;

import util.TimeSpan;

/**
 * 
 * Represents a response entity, containing the flight ID, runway, a time span
 * and an indicator for whether the flight is allowed to land/depart.
 *
 */
public class Response {
	private String flightID;
	private Runway runway;
	private TimeSpan timeSpan;
	private boolean isAllowedToLand;

	public Response(String flightID, Runway runway, TimeSpan timeSpan, boolean isAllowedToLand) {
		super();
		this.flightID = flightID;
		this.runway = runway;
		this.timeSpan = timeSpan;
		this.isAllowedToLand = isAllowedToLand;
	}

	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public Runway getRunway() {
		return runway;
	}

	public void setRunway(Runway runway) {
		this.runway = runway;
	}

	public TimeSpan getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(TimeSpan timeSpan) {
		this.timeSpan = timeSpan;
	}

	public boolean isAllowedToLand() {
		return isAllowedToLand;
	}

	public void setAllowedToLand(boolean isAllowedToLand) {
		this.isAllowedToLand = isAllowedToLand;
	}
}
