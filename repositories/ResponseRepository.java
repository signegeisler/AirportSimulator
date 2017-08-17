package repositories;

import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.topology.Target;

import agents.BaseAgent;
import entities.Response;
import factories.TemplateFactory;
import factories.TupleFactory;

public class ResponseRepository {

	private BaseAgent agent;
	
	public ResponseRepository(BaseAgent agent) {
		this.agent = agent;
	}
	
	/**
	 * Gets a response associated with a specific flight ID.
	 * @param flightId	 	the flight ID to use for the get
	 * @param p				the target for the get
	 * @return 				a response matching the get
	 */
	public Response getById(String flightId, Target p) {
		Template responseTemplate = TemplateFactory.createResponseTemplate(flightId);
		Tuple t = agent.get(responseTemplate, p);
		Response r = (Response)agent.getEntity(t);
		return r;
	}
	
	/**
	 * Puts a given response to the tuple space. 
	 * @param airplane	 	the response to put to the tuple space
	 * @param p				the target for the put
	 */
	public void put(Response response, Target p) {
		Tuple t = TupleFactory.createResponseTuple(response);
		agent.put(t, p);
	}
}