import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;


@SuppressWarnings("serial")
public class Disk extends PriorityQueue<Process>{
	
	/*
	 * Default Constructor
	 */
	public Disk() {
		
		super(burstTimeComparator);
	}
	
	/*
	 * Decrement every io process's burst by one.
	 */
	public void decrement() {
		
		Iterator<Process> iterator = this.iterator();
		
		while (iterator.hasNext()) {
			
			Process thisProcess = iterator.next();
			int oldBurst = thisProcess.peekNextIoBurst();
			int newBurst = oldBurst - 1;
			thisProcess.setNextIoBurst(newBurst);
		}
	}
	
	/**
	 * Assigns a comparator for the next IO burst of a Process.
	 * If the burst time is the same, compare the id for order.
	 */
    public static Comparator<Process> burstTimeComparator = new Comparator<Process>(){
         
        @Override
        public int compare(Process p1, Process p2) {
        	
        	if (p1.peekNextIoBurst() == p2.peekNextIoBurst()) {
        		
        		if (p1.getId() > p2.getId()) {
        			
        			return 1;
        		}
        		else {
        			
        			return -1;
        		}
        	}
        	else if (p1.peekNextIoBurst() > p2.peekNextIoBurst()) {
        	
                return 1;
        	}
        	else {
        		
        		return -1;
        	}
        }
        
    }; // End burstTimeComparator().
	
	/*
	 * Return true when the next io process is finished running.
	 */
	public boolean isCompleted() {
		
		if (this.isEmpty()) {
			
			return false;
		}
		else {
			
			if (this.peek().peekNextIoBurst() == 0) {
				
				this.peek().getNextIoBurst();
				return true;
			}
			else {
				
				return false;
			}
		}
		
	} 
	// END isCompleted()

} // END Class
