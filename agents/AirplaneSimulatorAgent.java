package agents;

import java.util.Calendar;

import org.cmg.resp.comp.Node;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.topology.PointToPoint;
import org.cmg.resp.topology.SocketPortAddress;

import entities.Airplane;
import factories.TemplateFactory;
import util.AirplaneGenerator;
import util.Constants;

public class AirplaneSimulatorAgent extends BaseAgent {

	private PointToPoint airportNodePort;
	private Node simulatorNode;
	
	public AirplaneSimulatorAgent(String name, Node simulatorNode, String airportIp) {
		super(name);
		airportNodePort = new PointToPoint(Constants.AIRPORT_NAME, new SocketPortAddress(airportIp, Constants.AIRPORT_PORT));
		this.simulatorNode = simulatorNode;
	}

	@Override
	protected void doRun() throws Exception {
		generateAirplanes();
	}
	
	/**
	 * Generates airplanes at small intervals. 
	 * @param simulatorNode	the node simulating airplanes
	 * @param airportNode	the node simulating the airport
	 * @throws InterruptedException
	 */
	private void generateAirplanes() throws InterruptedException {
		while (true) {
			Airplane airplane;
			do {
				Template template = TemplateFactory.createSatelliteTemplate();
				Tuple t = query(template, airportNodePort);
				Calendar time = (Calendar) t.getElementAt(0);
				airplane = AirplaneGenerator.createAirplane(time);
			} while (airplane.getRequestedTimeSpan() == null);

			AirplaneAgent airplaneAgent = new AirplaneAgent(airplane.getFlightID(), airplane, airportNodePort);
			simulatorNode.addAgent(airplaneAgent);
			Thread.sleep(1000 + (int) (Math.random() * 2000));
		}
	}
}