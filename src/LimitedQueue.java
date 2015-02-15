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
	
	/**
	 * Custom Constructor
	 * 
	 * @param limit
	 */
	public LimitedQueue(int limit) {
		
		this.limit = limit;
	}
	
	@Override
	/**
	 * Custom add() method to accommodate the limit feature.
	 * 
	 * @param - Process p
	 * @return - true if successful, false otherwise
	 */
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
	/**
	 * Custom remove method to mimic a deQueue method.
	 * 
	 * @return - Process object being removed
	 */
	public Process remove() {
		
		return super.removeFirst();
	}
	
	/**
	 * Returns the limit capacity of this queue
	 * 
	 * @return - limit
	 */
	public int getLimit() {
		return limit;
	}
	
} // End LimitedQueue Class
