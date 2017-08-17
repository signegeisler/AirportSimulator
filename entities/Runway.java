package entities;

import java.util.ArrayList;

import enums.AirplaneSize;
import util.TimeSpan;

/**
 * 
 * Represents a runway entity, containing the runway ID, flight size and
 * schedule with occupied time spans.
 *
 */
public class Runway {
	private String runID;
	private AirplaneSize size;
	private ArrayList<TimeSpan> schedule;

	public Runway(String runID, AirplaneSize size, ArrayList<TimeSpan> schedule) {
		super();
		this.runID = runID;
		this.size = size;
		this.setSchedule(schedule);
	}

	public String getRunID() {
		return runID;
	}

	public void setRunID(String runID) {
		this.runID = runID;
	}

	public AirplaneSize getSize() {
		return size;
	}

	public void setSize(AirplaneSize size) {
		this.size = size;
	}

	public ArrayList<TimeSpan> getSchedule() {
		return schedule;
	}

	public void setSchedule(ArrayList<TimeSpan> schedule) {
		this.schedule = schedule;
	}

}