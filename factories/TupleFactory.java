package factories;

import java.util.Calendar;

import org.cmg.resp.knowledge.Tuple;

import entities.Airplane;
import entities.Request;
import entities.Response;
import entities.Runway;
import enums.AirplaneSize;
import util.Constants;

/**
 * 
 * Factory for creating tuples.
 *
 */
public class TupleFactory {

	/**
	 * Creates a request tuple.
	 * @param request 	the request for which to create a tuple
	 * @return 			a new tuple containing the request
	 */
	public static Tuple createRequestTuple(Request request) {
		return new Tuple(request);
	}

	/**
	 * Creates an airplane tuple.
	 * @param airplane 	the airplane for which to create a tuple
	 * @return 			a new tuple containing the airplane and its ID
	 */
	public static Tuple createAirplaneTuple(Airplane airplane) {
		return new Tuple(airplane, airplane.getFlightID());
	}

	/**
	 * Creates a response tuple.
	 * @param response 	the response for which to create a tuple
	 * @return 			a new tuple containing the response and the 
	 * 					ID of the flight to receive the response
	 */
	public static Tuple createResponseTuple(Response response) {
		return new Tuple(response, response.getFlightID());
	}

	/**
	 * Creates a runway tuple.
	 * @param runway 	the runway for which to create a tuple
	 * @return 			a new tuple containing the runway, the ID of
	 * 					the runway and the size of the runway
	 */
	public static Tuple createRunwayTuple(Runway runway) {
		return new Tuple(runway, runway.getRunID(), runway.getSize());
	}

	/**
	 * Creates a runway lock tuple.
	 * @param size	 	the airplane size to be used to create a tuple.
	 * @return 			a new tuple containing the runway lock and the 
	 * 					airplane size
	 */
	public static Tuple createRunwaysLockTuple(AirplaneSize size) {
		return new Tuple(Constants.RUNWAYS_LOCK, size);
	}

	/**
	 * Creates a queue lock tuple.
	 * @return 	a new tuple containing the queue lock. 
	 */
	public static Tuple createQueueLockTuple() {
		return new Tuple(Constants.QUEUE_LOCK);
	}

	/**
	 * Creates a queue head tuple.
	 * @param request		the request to be put in the tuple
	 * @param elementIndex	the desired element index to be put in the 
	 * 						tuple
	 * @return				a new tuple containing the request and the 
	 * 						element index as the head of the queue
	 */
	public static Tuple createQueueHeadTuple(Request request, int elementIndex) {
		return new Tuple(request, Constants.QUEUE_HEAD, elementIndex);
	}

	/**
	 * Creates a queue tail tuple.
	 * @param request		the request to be put in the tuple
	 * @param elementIndex	the desired element index to be put in the 
	 * 						tuple
	 * @return				a new tuple containing the request and the 
	 * 						element index as the tail of the queue
	 */
	public static Tuple createQueueTailTuple(Request request, int elementIndex) {
		return new Tuple(request, Constants.QUEUE_TAIL, elementIndex);
	}

	/**
	 * Creates a queue element tuple.
	 * @param request		the request to be put in the tuple
	 * @param elementIndex	the desired element index to be put in the 
	 * 						tuple
	 * @return				a new tuple containing the request and the 
	 * 						element index as an element in the queue
	 */
	public static Tuple createQueueElementTuple(Request request, int elementIndex) {
		return new Tuple(request, Constants.QUEUE_ELEMENT, elementIndex);
	}

	/**
	 * Creates a satellite tuple
	 * @param time	the time to be used to create a tuple
	 * @return		a new tuple containing the time
	 */
	public static Tuple createSatelliteTuple(Calendar time) {
		return new Tuple(time);
	}
}