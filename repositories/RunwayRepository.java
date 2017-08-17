package repositories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.topology.Target;

import agents.BaseAgent;
import entities.Runway;
import enums.AirplaneSize;
import factories.TemplateFactory;
import factories.TupleFactory;

public class RunwayRepository {

	private BaseAgent agent;

	public RunwayRepository(BaseAgent agent) {
		this.agent = agent;
	}

	/**
	 * Queries all runways with a given size.
	 * @param size	the runway size
	 * @param p 	the target for the query
	 * @return 		all runways with the given size.
	 */
	public List<Runway> queryFromSize(AirplaneSize size, Target p) {
		LinkedList<Tuple> runwayTuples = agent.queryAll(TemplateFactory.createRunwayTemplate(size));
		Iterator<Tuple> iterator = runwayTuples.iterator();
		List<Runway> runways = new ArrayList<Runway>();
		while (iterator.hasNext()) {
			Tuple t = (Tuple) iterator.next();
			runways.add((Runway) agent.getEntity(t));
		}

		return runways;
	}

	/**
	 * Queries a runway associated with a specific ID.
	 * @param runwayId	the ID to use for the query
	 * @param p			the target for the query
	 * @return			a runway mathcing the query
	 */
	public Runway queryById(String runwayId, Target p) {
		Template runwayTemplate = TemplateFactory.createRunwayTemplate(runwayId);
		Tuple t = agent.query(runwayTemplate, p);
		Runway r = (Runway) agent.getEntity(t);
		return r;
	}

	/**
	 * Updates a runway.
	 * @param runway	the runway to update
	 * @param p			the target for the update
	 */
	public void update(Runway runway, Target p) {
		Template runwayTemplate = TemplateFactory.createRunwayTemplate(runway.getRunID());
		agent.get(runwayTemplate, p);
		agent.put(TupleFactory.createRunwayTuple(runway), p);
	}

	/**
	 * Retrieves the runway lock.
	 * @param p	the target
	 */
	public void lock(Target p, AirplaneSize size) {
		Template lockTemplate = TemplateFactory.createRunwaysLockTemplate(size);
		agent.get(lockTemplate, p);
	}

	/**
	 * Releases the runway lock.
	 * @param p	the target
	 */
	public void releaseLock(Target p, AirplaneSize size) {
		Tuple t = TupleFactory.createRunwaysLockTuple(size);
		agent.put(t, p);
	}
}