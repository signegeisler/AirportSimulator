package factories;

import java.util.Calendar;

import org.cmg.resp.knowledge.ActualTemplateField;
import org.cmg.resp.knowledge.FormalTemplateField;
import org.cmg.resp.knowledge.Template;

import entities.Airplane;
import entities.Request;
import entities.Response;
import entities.Runway;
import enums.AirplaneSize;
import util.Constants;

/**
 * 
 * Factory for creating templates.
 *
 */
public class TemplateFactory {
	
	/**
	 * Creates a template matching any airplane.
	 * @return a template matching any airplane
	 */
	public static Template createAirplaneTemplate() {
		return new Template(new FormalTemplateField(Airplane.class),
							new FormalTemplateField(String.class));
	}
	
	/**
	 * Creates a template matching an airplane with a specific ID.
	 * @param flightId	the flight ID to match
	 * @return			a template matching an airplane with the 
	 * 					given flight ID
	 */
	public static Template createAirplaneTemplate(String flightId) {
		return new Template(new FormalTemplateField(Airplane.class),
							new ActualTemplateField(flightId));
	}
	
	/**
	 * Creates a template matching any runway.
	 * @return a template matching any runway
	 */
	public static Template createRunwayTemplate() {
		return new Template(new FormalTemplateField(Runway.class),
							new FormalTemplateField(String.class),
							new FormalTemplateField(AirplaneSize.class));
	}
	
	/**
	 * Creates a template matching a runway with a specific size.
	 * @param size	the runway size to match
	 * @return		a template matching a runway with the given 
	 * 				size.
	 */
	public static Template createRunwayTemplate(AirplaneSize size) {
		return new Template(new FormalTemplateField(Runway.class),
							new FormalTemplateField(String.class),
							new ActualTemplateField(size));
	}
	
	/**
	 * Creates a template matching a runway with a specific ID.
	 * @param runwayId	the runway ID to match
	 * @return			a template matching a runway with the 
	 * 					given runway ID
	 */
	public static Template createRunwayTemplate(String runwayId) {
		return new Template(new FormalTemplateField(Runway.class),
							new ActualTemplateField(runwayId),
							new FormalTemplateField(AirplaneSize.class));
	}
	
	/**
	 * Creates a template matching the runway lock.
	 * @param size	the size of the runway
	 * @return		a template matching the runway lock
	 */
	public static Template createRunwaysLockTemplate(AirplaneSize size) {
		return new Template(new ActualTemplateField(Constants.RUNWAYS_LOCK),
							new ActualTemplateField(size));
	}
	
	/**
	 * Creates a template matching the queue lock.
	 * @return a template matching any queue lock
	 */
	public static Template createQueueLockTemplate() {
		return new Template(new ActualTemplateField(Constants.QUEUE_LOCK));
	}

	/**
	 * Creates a template matching a response with a specific 
	 * flight ID.
	 * @param flightId	the flight ID to match
	 * @return			a template matching a response with the 
	 * 					given flight ID
	 */
	public static Template createResponseTemplate(String flightId) {
		return new Template(new FormalTemplateField(Response.class),
							new ActualTemplateField(flightId));
	}
	
	/**
	 * Creates a template matching the head of the queue.
	 * @return a template matching the head of the queue
	 */
	public static Template createQueueHeadTemplate() {
		return new Template(new FormalTemplateField(Request.class),
				 	 		new ActualTemplateField(Constants.QUEUE_HEAD),
				 	 		new FormalTemplateField(Integer.class));
	}
	
	/**
	 * Creates a template matching the tail of the queue.
	 * @return a template matching the tail of the queue
	 */
	public static Template createQueueTailTemplate() {
		return new Template(new FormalTemplateField(Request.class),
			 	 			new ActualTemplateField(Constants.QUEUE_TAIL),
			 	 			new FormalTemplateField(Integer.class));
	}
	
	/**
	 * Creates a template matching an element of the queue with 
	 * a specific index.
	 * @param elementNumber	the index of the element in the queue
	 * @return				a template matching the element in the
	 * 						queue with the given index
	 */
	public static Template createQueueElementTemplate(int elementNumber) {
		return new Template(new FormalTemplateField(Request.class),
 	 						new FormalTemplateField(String.class),
 	 						new ActualTemplateField(elementNumber));
	}
	
	/**
	 * Creates a template matching any calendar.
	 * @return a template matching the calendar
	 */
	public static Template createSatelliteTemplate(){
		return new Template(new FormalTemplateField(Calendar.class));
	}
}