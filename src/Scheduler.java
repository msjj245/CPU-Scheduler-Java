import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public abstract class Scheduler {
	
	protected Clock theClock;
	public LimitedQueue<Process> readyQueue;
	public Queue<Process> diskQueue;
	public Queue<Process> ioQueue;
	public LimitedQueue<Process> CPU;
	
	/**
	 * Default Constructor
	 */
	public Scheduler(ArrayList<Process> processList) {
		
		theClock = new Clock();
		readyQueue = new LimitedQueue<Process>(3);
		diskQueue = new LinkedList<Process>();
		ioQueue = new LinkedList<Process>();
		CPU = new LimitedQueue<Process>(1);		
		
	} // End Default Constructor
	
	/**
	 * Begin processing here
	 */
	public abstract void begin();
	
	public void printStatus() {
	
		System.out.printf("  Time: %d\n\tCPU:\t%s\n\tJob Queue:\t%s\n\tReady Queue:\t%s\n\tDisk Queue:\t%s\n\tIOW Queue:\t%s\n", 
							theClock.getTime(), 
							listContents((LinkedList<Process>) CPU),
							// listContents(jobQueue),
							listContents((LinkedList<Process>) readyQueue),
							listContents((LinkedList<Process>) diskQueue),
							listContents((LinkedList<Process>) ioQueue));
	}
	
	public String listContents(LinkedList<Process> list) {
		
		String ret = "";
		for (int i = 0; i < list.size(); i++) {
			ret += list.get(i).getId() + ", ";
		}
		ret = ret.replaceAll(", $", "");
		return ret;
	}
	
	public void incrementClock(int i) {
		
		for (int j = 0; j > i; j++) {
			theClock.tick();
		}
	}
	
	public abstract void loadProcesses(ArrayList<Process> processList);
	
	
} // End Scheduler Class
