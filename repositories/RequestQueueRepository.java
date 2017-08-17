package repositories;

import org.cmg.resp.knowledge.Template;
import org.cmg.resp.knowledge.Tuple;
import org.cmg.resp.topology.Target;

import agents.BaseAgent;
import entities.Request;
import factories.TemplateFactory;
import factories.TupleFactory;

public class RequestQueueRepository {

	private BaseAgent agent;
	
	public RequestQueueRepository(BaseAgent agent) {
		this.agent = agent;
	}
	
	/**
	 * Removes the head of the queue, blocking if the queue is
	 * empty.
	 * @param p	the target
	 * @return	the head of the queue
	 */
	public Request remove(Target p) {
		Template headTemplate = TemplateFactory.createQueueHeadTemplate();
		Tuple headTuple = agent.get(headTemplate, p);
		
		lock(p);
		int headIndex = (int)headTuple.getElementAt(2);
		Template nextElementTemplate = TemplateFactory.createQueueElementTemplate(headIndex+1);
		Tuple nextElement = agent.getp(nextElementTemplate);
		
		// If head is the only element in queue
		if (nextElement == null) {
			releaseLock(p);
			return (Request)agent.getEntity(headTuple);
		}

		int newHeadIndex = (int)nextElement.getElementAt(2);
		Tuple newHeadTuple = TupleFactory.createQueueHeadTuple((Request)agent.getEntity(nextElement), newHeadIndex);
		agent.put(newHeadTuple, p);
		releaseLock(p);
		return (Request)agent.getEntity(headTuple);
	}
	
	/**
	 * Adds a given request to the queue
	 * @param request	the request to add to the queue
	 * @param p			the target
	 */
	public void add(Request request, Target p) {
		lock(p);
		Template headTemplate = TemplateFactory.createQueueHeadTemplate();
		Tuple headTuple = agent.queryp(headTemplate);
		
		// If queue has no head
		if (headTuple == null) {
			headTuple = TupleFactory.createQueueHeadTuple(request, 0);
			agent.put(headTuple, p);
			releaseLock(p);
			return;
		}
		
		int headIndex = (int)headTuple.getElementAt(2);
		Template tailTemplate = TemplateFactory.createQueueTailTemplate();
		Tuple tailTuple = agent.getp(tailTemplate);
		
		// If queue has no tail
		if (tailTuple == null) {
			tailTuple = TupleFactory.createQueueTailTuple(request, headIndex+1);
			agent.put(tailTuple, p);
			releaseLock(p);
			return;
		}
		
		// If queue has both head and tail
		int tailIndex = (int)tailTuple.getElementAt(2);
		Tuple oldTailTuple = TupleFactory.createQueueElementTuple((Request)agent.getEntity(tailTuple), tailIndex);
		Tuple newTailTuple = TupleFactory.createQueueTailTuple(request, tailIndex+1);
		
		agent.put(oldTailTuple, p);
		agent.put(newTailTuple, p);
		releaseLock(p);
	}
	
	/**
	 * Retrieves the queue lock.
	 * @param p	the target
	 */
	private void lock(Target p) {
		Template lockTemplate = TemplateFactory.createQueueLockTemplate();
		agent.get(lockTemplate, p);
	}
	
	/**
	 * Releases the queue lock.
	 * @param p the target
	 */
	private void releaseLock(Target p) {
		Tuple t = TupleFactory.createQueueLockTuple();
		agent.put(t, p);
	}
}