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
		
        super.addLast(p);
        while (size() > limit) { 
        	super.removeLast(); 
        	System.out.println("That one didn't fit in the readyQueue.");
        	return false;
        }
        return true;
        
    }
	
	@Override
	public Process remove() {
		
		return super.removeFirst();
	}
	
	public int getLimit() {
		return limit;
	}
	
} // End LimitedQueue Class
