/**
 * 
 * Class:	LimitedQueue
 * 
 * @author 	Magdalene Benson	<mebenson12@winona.edu>
 * @date	2/09/2015
 * 
 * A custom class to implement a Queue of limited size.
 */

import java.util.LinkedList;

@SuppressWarnings({ "serial", "hiding" })
public class LimitedQueue<Process> extends LinkedList<Process> {
	
	private int limit;
	
	public LimitedQueue(int limit) {
		
		this.limit = limit;
	}
	
	@Override
    public boolean add(Process p) {
		
		// if it can fit, add it
		if (size() < limit) {
			
			super.addLast(p);
			return true;
		}
		else {
			
			return false;
		}
    } // End custom add().
	
	@Override
	public Process remove() {
		
		return super.removeFirst();
	}
	
	/**
	 * retunr
	 * @return
	 */
	public int getLimit() {
		return limit;
	}
	
} // End LimitedQueue Class
