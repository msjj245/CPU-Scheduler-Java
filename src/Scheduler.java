import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public abstract class Scheduler {
	
	protected Clock theClock;
	protected LimitedQueue<Process> readyQueue;
	protected Queue<Process> ioWaitQueue;
	protected PriorityQueue<Process> jobQueue;
	protected LimitedQueue<Process> CPU;
	protected LinkedList<Process> Disk;
	
	/**
	 * Default Constructor
	 */
	public Scheduler(ArrayList<Process> processList) {
		
		theClock = Clock.getInstance();
		readyQueue = new LimitedQueue<Process>(3);
		ioWaitQueue = new LinkedList<Process>();
		CPU = new LimitedQueue<Process>(1);
		Disk = new LinkedList<Process>();
		
	} // End Default Constructor
	
	/**
	 * Begin scheduling algorithm
	 */
	public abstract void contextSwitch();
	
	/**
	 * Loads all processes into the job queue,
	 * then 3 processes to the ready queue.
	 * 
	 * @param processList
	 */
	public abstract void loadProcesses(ArrayList<Process> processList);
	
	/**
	 * Prints the current state of the CPU Scheduler.
	 */
	public abstract void printState();
	
	
} // End Scheduler Class
