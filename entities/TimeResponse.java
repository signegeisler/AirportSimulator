package entities;

import util.TimeSpan;

/**
 * 
 * Represents a time response entity, containing a time span and a runway.
 *
 */
public class TimeResponse {
	private TimeSpan timeSpan;
	private Runway runway;

	public TimeResponse(TimeSpan timeSpan, Runway runway) {
		super();
		this.timeSpan = timeSpan;
		this.runway = runway;
	}

	public TimeSpan getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(TimeSpan timeSpan) {
		this.timeSpan = timeSpan;
	}

	public Runway getRunway() {
		return runway;
	}

	public void setRunway(Runway runway) {
		this.runway = runway;
	}

}
