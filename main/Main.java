package main;

import java.util.ArrayList;

import org.cmg.resp.behaviour.Agent;
import org.cmg.resp.comp.Node;
import org.cmg.resp.knowledge.ts.TupleSpace;
import org.cmg.resp.topology.SocketPort;

import agents.AirplaneSimulatorAgent;
import agents.ControlTowerAgent;
import agents.SatelliteAgent;
import entities.Runway;
import enums.AirplaneSize;
import factories.TupleFactory;
import log.CustomLogger;
import util.Constants;
import util.TimeSpan;
import util.Util;

public class Main {
	static Util util = new Util();
	static CustomLogger logger = new CustomLogger();
	
	/**
	 * Serves as entry point for the application. Handles necessary
	 * initialization of tuple spaces, nodes, agents.
	 */
	public static void main(String[] argv) {
				
		try {
			TupleSpace airportTupleSpace = new TupleSpace();
			TupleSpace airplaneSimulatorTupleSpace = new TupleSpace();

			SocketPort airportPort = new SocketPort(Constants.AIRPORT_PORT);
			SocketPort airplaneSimulatorPort = new SocketPort(Constants.AIRPLANE_SIMULATOR_PORT);

			Node airportNode = new Node(Constants.AIRPORT_NAME, airportTupleSpace);
			Node airplaneSimulatorNode = new Node(Constants.AIRPLANE_SIMULATOR_NAME, airplaneSimulatorTupleSpace);

			initAirportTuples(airportNode);

			Agent controlTowerAgent1 = new ControlTowerAgent("Control Tower 1");
			Agent controlTowerAgent2 = new ControlTowerAgent("Control Tower 2");
			Agent satelliteAgent = new SatelliteAgent("Sputnik");
			Agent airplaneSimulatorAgent = new AirplaneSimulatorAgent("Airplane simulator", airplaneSimulatorNode, "127.0.0.1");

			airportNode.addPort(airportPort);
			airplaneSimulatorNode.addPort(airplaneSimulatorPort);
			airportNode.addAgent(controlTowerAgent1);
			airportNode.addAgent(controlTowerAgent2);
			airportNode.addAgent(satelliteAgent);
			airplaneSimulatorNode.addAgent(airplaneSimulatorAgent);

			airportNode.start();
			airplaneSimulatorNode.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes airport tuples. 
	 * @param node	the node simulating the airport
	 */
	private static void initAirportTuples(Node node) {
		// Create runways
		Runway smallRunway = new Runway("1", AirplaneSize.SMALL, new ArrayList<TimeSpan>());
		Runway bigRunway = new Runway("2", AirplaneSize.BIG, new ArrayList<TimeSpan>());
		node.put(TupleFactory.createRunwayTuple(smallRunway));
		node.put(TupleFactory.createRunwayTuple(bigRunway));
		node.put(TupleFactory.createSatelliteTuple(util.createTime(15, 30)));

		// Create locks
		node.put(TupleFactory.createRunwaysLockTuple(AirplaneSize.SMALL));
		node.put(TupleFactory.createRunwaysLockTuple(AirplaneSize.BIG));
		node.put(TupleFactory.createQueueLockTuple());
	}
}