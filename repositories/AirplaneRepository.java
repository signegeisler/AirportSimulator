package repositories;

import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.topology.Target;

import agents.BaseAgent;
import entities.Airplane;
import factories.TemplateFactory;
import factories.TupleFactory;

public class AirplaneRepository {

	private BaseAgent agent;

	public AirplaneRepository(BaseAgent agent) {
		this.agent = agent;
	}

	/**
	 * Queries an airplane associated with a specific ID.
	 * @param airplaneId 	the ID to use for the query
	 * @param p				the target for the query
	 * @return 				an airplane matching the query
	 */
	public Airplane queryById(String airplaneId, Target p) {
		Template airplaneTemplate = TemplateFactory.createAirplaneTemplate(airplaneId);
		Tuple t = agent.query(airplaneTemplate, p);
		Airplane a = (Airplane) agent.getEntity(t);
		return a;
	}

	/**
	 * Gets an airplane associated with a specific ID.
	 * @param airplaneId 	the ID to use for the get
	 * @param p				the target for the get
	 * @return 				an airplane matching the get
	 */
	public Airplane getById(String airplaneId, Target p) {
		Template airplaneTemplate = TemplateFactory.createAirplaneTemplate(airplaneId);
		Tuple t = agent.get(airplaneTemplate, p);
		Airplane a = (Airplane) agent.getEntity(t);
		return a;
	}

	/**
	 * Puts a given airplane to the tuple space. 
	 * @param airplane	 	the airplane to put to the tuple space
	 * @param p				the target for the put
	 */
	public void put(Airplane airplane, Target p) {
		Tuple t = TupleFactory.createAirplaneTuple(airplane);
		agent.put(t, p);
	}
}