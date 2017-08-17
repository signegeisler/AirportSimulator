package interfaces;

import java.util.List;

import entities.Request;
import entities.Runway;
import entities.TimeResponse;

public interface IScheduler {
	TimeResponse schedule(Request request, List<Runway> runways);
}