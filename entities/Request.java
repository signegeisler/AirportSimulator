package entities;

import enums.RequestType;
import util.TimeSpan;

/**
 * 
 * Represents a request entity, containing a flight ID, request type, request
 * status and a time span.
 *
 */
public class Request {
	private String flightID;
	private RequestType type;
	private String status;
	private TimeSpan time;

	public Request(String flightID, RequestType type, String status, TimeSpan time) {
		super();
		this.flightID = flightID;
		this.type = type;
		this.status = status;
		this.time = time;
	}

	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TimeSpan getTime() {
		return time;
	}

	public void setTime(TimeSpan time) {
		this.time = time;
	}
}
