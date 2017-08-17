package util;

import java.util.Calendar;

/**
 * 
 * Represents a time span, spanning from start to end.
 *
 */
public class TimeSpan implements Comparable<TimeSpan> {

	private Calendar start;
	private Calendar end;

	public TimeSpan(Calendar start, Calendar end) {
		super();
		this.start = start;
		this.end = end;
	}

	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}
	
	@Override
	public int compareTo(TimeSpan compareSpan) {
		Util util = new Util();
		return util.compareTo(start, compareSpan.getStart());
	}

	public String toString(){
		
		String startTime; 
		String endTime;
		
		if(start.get(Calendar.MINUTE) < 10){
			startTime = start.get(Calendar.HOUR_OF_DAY) + ":0" + start.get(Calendar.MINUTE);
		} else {
			startTime = start.get(Calendar.HOUR_OF_DAY) + ":" + start.get(Calendar.MINUTE);
		}
		
		if(end.get(Calendar.MINUTE) < 10){
			endTime = end.get(Calendar.HOUR_OF_DAY) + ":0" + end.get(Calendar.MINUTE);
		} else {
			endTime = end.get(Calendar.HOUR_OF_DAY) + ":" + end.get(Calendar.MINUTE);
		}
		
		return startTime + " to " + endTime;
	}

}
