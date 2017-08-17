package entities;

import util.TimeSpan;
import enums.AirplaneSize;
import enums.AirplaneType;

/**
 * 
 * Represents an airplane entity, containing the flight ID, flight type, flight
 * size and a time span.
 *
 */
public class Airplane {
	private String flightID;
	private AirplaneType flightType;
	private AirplaneSize size;
	private TimeSpan timespan;

	public Airplane(String flightID, AirplaneType flightType, AirplaneSize size, TimeSpan timespan) {
		super();
		this.flightID = flightID;
		this.flightType = flightType;
		this.size = size;
		this.timespan = timespan;
	}

	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public AirplaneType getFlightType() {
		return flightType;
	}

	public void setFlightType(AirplaneType flightType) {
		this.flightType = flightType;
	}

	public AirplaneSize getSize() {
		return size;
	}

	public void setSize(AirplaneSize size) {
		this.size = size;
	}

	public TimeSpan getRequestedTimeSpan() {
		return timespan;
	}

	public void setRequestedTimeSpan(TimeSpan timespan) {
		this.timespan = timespan;
	}
}