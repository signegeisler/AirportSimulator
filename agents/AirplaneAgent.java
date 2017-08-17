package agents;

import java.util.Calendar;

import org.cmg.resp.topology.PointToPoint;

import entities.Airplane;
import entities.Request;
import entities.Response;
import entities.Runway;
import enums.RequestType;
import log.CustomLogger;
import repositories.AirplaneRepository;
import repositories.RequestQueueRepository;
import repositories.ResponseRepository;
import repositories.RunwayRepository;
import repositories.SatelliteRepository;
import util.AirplaneGenerator;
import util.Constants;
import util.TimeSpan;
import util.Util;

/**
 *
 * Identifies an AirplaneAgent which defines the behavior of a single
 * airplane.
 *
 */
public class AirplaneAgent extends BaseAgent {

	private PointToPoint airportNodePort;
	private Airplane airplane;
	private RequestQueueRepository queueRepository;
	private ResponseRepository responseRepository;
	private AirplaneRepository airplaneRepository;
	private RunwayRepository runwayRepository;
	private SatelliteRepository satelliteRepository;

	public AirplaneAgent(String name, Airplane airplane, PointToPoint airportNodePort) {
		super(name);
		this.airportNodePort = airportNodePort;
		this.airplane = airplane;
		queueRepository = new RequestQueueRepository(this);
		responseRepository = new ResponseRepository(this);
		airplaneRepository = new AirplaneRepository(this);
		runwayRepository = new RunwayRepository(this);
		satelliteRepository = new SatelliteRepository(this);
	}

	/**
	 * Identifies this agent's behavior. Creates a new request and puts it in 
	 * queue for the {@link ControlTowerAgent} to handle. Lands/departs 
	 * according to the received response from the control tower.   
	 */
	@Override
	protected void doRun() {

		CustomLogger logger = new CustomLogger();
		Util util = new Util();

		try {
			airplaneRepository.put(airplane, airportNodePort);
			Request request = new Request(airplane.getFlightID(), getRandomRequestType(), null,
					airplane.getRequestedTimeSpan());
			queueRepository.add(request, airportNodePort);

			// Wait for response from control tower
			Response response = responseRepository.getById(airplane.getFlightID(), airportNodePort);

			if (!response.isAllowedToLand()) {
				logger.log(airplane.getFlightID() + ":" + Constants.TAB
						+ "Not allowed to land. Redirecting to another airport.");
				airplaneRepository.getById(airplane.getFlightID(), airportNodePort);
				AirplaneGenerator.removeFlight(airplane.getFlightID());
				return;
			}

			String action = request.getType() == RequestType.LANDING ? "LAND on " : "DEPART from ";
			logger.log("[" + util.asString(satelliteRepository.getTime(airportNodePort)) + "] " + airplane.getFlightID()
					+ ":" + Constants.TAB + "Scheduled to " + action + response.getRunway().getSize() + " Runway "
					+ response.getRunway().getRunID() + " in the timespan " + response.getTimeSpan().toString());

			waitForLandingTime(util, response);
			Runway runway = updateRunway(response);

			action = request.getType() == RequestType.LANDING ? "LANDED on " : "DEPARTED from ";
			logger.log("[" + util.asString(satelliteRepository.getTime(airportNodePort)) + "] " + airplane.getFlightID()
					+ ":" + Constants.TAB + action + runway.getSize() + " Runway " + runway.getRunID());

			// Remove the airplane from the TS
			airplaneRepository.getById(airplane.getFlightID(), airportNodePort);
			AirplaneGenerator.removeFlight(airplane.getFlightID());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Waits for the application time to reach a time contained in the time span 
	 * of the {@link Response} object, i.e. a time when the airplane is allowed 
	 * to land/depart.
	 * @param util		an instance of the {@link util.Util} class
	 * @param response 	the response received from the control tower
	 * @throws InterruptedException
	 */
	private void waitForLandingTime(Util util, Response response) throws InterruptedException {
		// Wait until airplane is allowed to land
		while (true) {
			Calendar actualTime = satelliteRepository.getTime(airportNodePort);
			TimeSpan arrivalSpan = response.getTimeSpan();
			if (util.isOverlapping(new TimeSpan(actualTime, actualTime), arrivalSpan)) {
				break;
			}
			Thread.sleep(1000);
		}
	}

	/**
	 * Updates the schedule for a runway when an airplane has landed/
	 * departed by removing the scheduled time.
	 * @param response	the response the airplane received. 
	 * @return			an updated version of the runway the airplane landed
	 * 					on/departed from. 
	 */
	private Runway updateRunway(Response response) {
		runwayRepository.lock(airportNodePort, airplane.getSize());

		// Retrieve new runway instance in case it has been updated by the
		// control tower
		Runway runway = runwayRepository.queryById(response.getRunway().getRunID(), airportNodePort);

		for (TimeSpan t : runway.getSchedule()) {
			if (t.compareTo(response.getTimeSpan()) == 0) {
				runway.getSchedule().remove(t);
				break;
			}
		}

		runwayRepository.update(runway, airportNodePort);
		runwayRepository.releaseLock(airportNodePort, airplane.getSize());
		return runway;
	}

	/**
	 * Generates a request type (landing or departure) at random.
	 * @return	a random request type.
	 */
	private RequestType getRandomRequestType() {
		int i = 1 + (int) (Math.random() * 2);
		switch (i) {
		case 1:
			return RequestType.LANDING;
		case 2:
			return RequestType.DEPARTURE;
		}
		return null;
	}
}