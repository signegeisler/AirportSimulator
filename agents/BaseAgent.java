package agents;

import java.io.IOException;
import java.util.LinkedList;

import org.cmg.resp.behaviour.Agent;
import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.topology.Target;

/**
 * 
 * Base class for agents providing methods for primitives interacting with tuple
 * spaces.
 *
 */
public abstract class BaseAgent extends Agent {

	public BaseAgent(String name) {
		super(name);
	}

	public boolean put(Tuple t, Target p) {
		try {
			return super.put(t, p);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Tuple get(Template t, Target p) {
		try {
			return super.get(t, p);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Tuple query(Template t, Target p) {
		try {
			return super.query(t, p);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Tuple getp(Template t) {
		return super.getp(t);
	}

	public Tuple queryp(Template t) {
		return super.queryp(t);
	}

	public LinkedList<Tuple> getAll(Template t) {
		return super.getAll(t);
	}

	public LinkedList<Tuple> queryAll(Template t) {
		return super.queryAll(t);
	}

	public Object getEntity(Tuple t) {
		if (t == null)
			return null;

		return t.getElementAt(0);
	}
}