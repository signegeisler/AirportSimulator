package agents;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.cmg.resp.topology.Self;

import repositories.SatelliteRepository;
import util.Constants;
import util.Util;

/**
*
* Identifies a SatelliteAgent which defines the behavior of a single
* time controlling satellite.
*
*/
public class SatelliteAgent extends BaseAgent {

	private SatelliteRepository satelliteRepository;
	
	private int timer;
	private Calendar satTime = new GregorianCalendar();
	Util util = new Util();
	
	public SatelliteAgent(String name) {
		super(name);
		satelliteRepository = new SatelliteRepository(this);
	}

	/**
	 * Identifies this agent's behavior. Increments the time by 
	 * one minute every 500 milliseconds. 
	 */
	@Override
	protected void doRun() throws Exception {
		
		while(true){
			Calendar calTime = satelliteRepository.getTime(Self.SELF);
			timer = (calTime.get(Calendar.MINUTE) + calTime.get(Calendar.HOUR_OF_DAY) * 60 + 1) % Constants.MINUTES_IN_DAY; 
			satTime.set(Calendar.MINUTE, timer % 60);
			satTime.set(Calendar.HOUR_OF_DAY, timer/60);
			
			satelliteRepository.updateTime(satTime, Self.SELF);
			
			Thread.sleep(500);
		}	
	}
}
