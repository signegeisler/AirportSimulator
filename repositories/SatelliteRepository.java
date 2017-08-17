package repositories;

import java.util.Calendar;

import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.topology.Target;

import agents.BaseAgent;
import factories.TemplateFactory;
import factories.TupleFactory;

public class SatelliteRepository {
	
	private BaseAgent agent;
	
	public SatelliteRepository(BaseAgent agent){
		this.agent = agent;
	}
	
	/**
	 * Gets the current time.
	 * @param p	the target
	 * @return	the current time
	 */
	public Calendar getTime(Target p){
		Template satelliteTemplate = TemplateFactory.createSatelliteTemplate();
		Tuple t = agent.query(satelliteTemplate, p);
		Calendar time = (Calendar)agent.getEntity(t);
		return time;
	}
	
	/**
	 * Updates the time based on a given new time
	 * @param newTime	the new time to update to
	 * @param p			the target
	 */
	public void updateTime(Calendar newTime, Target p){
		Template satelliteTemplate = TemplateFactory.createSatelliteTemplate();
		agent.get(satelliteTemplate, p);
		agent.put(TupleFactory.createSatelliteTuple(newTime), p);
	}
}
