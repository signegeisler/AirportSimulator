package agents;

import java.util.List;

import org.cmg.resp.topology.Self;

import entities.Airplane;
import entities.Request;
import entities.Response;
import entities.Runway;
import entities.TimeResponse;
import interfaces.IScheduler;
import log.CustomLogger;
import repositories.AirplaneRepository;
import repositories.RequestQueueRepository;
import repositories.ResponseRepository;
import repositories.RunwayRepository;
import repositories.SatelliteRepository;
import services.ScheduleService;
import util.Constants;
import util.Util;

/**
 * 
 * Identifies a ControlTowerAgent which defines the behavior of a 
 * single control tower.
 *
 */
public class ControlTowerAgent extends BaseAgent {

	private CustomLogger logger = new CustomLogger();
	private RunwayRepository runwayRepository;
	private AirplaneRepository airplaneRepository;
	private ResponseRepository responseRepository;
	private RequestQueueRepository queueRepository;
	private SatelliteRepository satelliteRepository;

	public ControlTowerAgent(String name) {
		super(name);
		runwayRepository = new RunwayRepository(this);
		airplaneRepository = new AirplaneRepository(this);
		responseRepository = new ResponseRepository(this);
		queueRepository = new RequestQueueRepository(this);
		satelliteRepository = new SatelliteRepository(this);
		
	}

	/**
	 * Identifies this agent's behavior. Waits for requests to handle.
	 * Generates a response for each request.   
	 */
	@Override
	protected void doRun() throws InterruptedException {
		System.out.println(getLocalAddresses());
		Util util = new Util();
		while (true) {
			//Wait for element in queue
			Request request = queueRepository.remove(Self.SELF);
			
			Airplane airplane = airplaneRepository.queryById(request.getFlightID(), Self.SELF);

			logger.log("[" + util.asString(satelliteRepository.getTime(Self.SELF)) + "] " + this.getName() + ":" + Constants.TAB + airplane.getFlightID() + " requested " + request.getType() + ". Size: " + airplane.getSize() + ". Time: " + airplane.getRequestedTimeSpan().toString());

			runwayRepository.lock(Self.SELF, airplane.getSize());
			Response response = createResponse(request, airplane);
			runwayRepository.releaseLock(Self.SELF, airplane.getSize());
			responseRepository.put(response, Self.SELF);
		}
	}

	/**
	 * Creates a {@link Response} object based on a request and an airplane.
	 * @param request	the request received from an airplane
	 * @param airplane	the airplane from which the request is received
	 * @return			a response containing the planned time span and 
	 * 					runway for an airplane to land on/depart from or 
	 * 					containing null objects if no valid time span 
	 * 					could be found. 
	 * 
	 */
	private Response createResponse(Request request, Airplane airplane) {
		List<Runway> runways = runwayRepository.queryFromSize(airplane.getSize(), Self.SELF);
		
		IScheduler scheduler = new ScheduleService();
		TimeResponse timeResponse = scheduler.schedule(request, runways);
		
		Response response;
		if (timeResponse == null) {
			response = new Response(airplane.getFlightID(), null, null, false);
		} else {
			runwayRepository.update(timeResponse.getRunway(), Self.SELF);
			response = new Response(airplane.getFlightID(), timeResponse.getRunway(), timeResponse.getTimeSpan(), true);
		}
		return response;
	}
}