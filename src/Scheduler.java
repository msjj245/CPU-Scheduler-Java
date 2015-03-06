import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public abstract class Scheduler {
	
	protected Clock theClock;
	protected PriorityQueue<Process> readyQueue;
	protected Queue<Process> ioWaitQueue;
	protected LinkedList<Process> jobQueue;
	protected LimitedQueue<Process> CPU;
	protected Disk Disk;
	
	/**
	 * Default Constructor
	 */
	public Scheduler(ArrayList<Process> processList) {
		
		theClock = Clock.getInstance();
		ioWaitQueue = new LinkedList<Process>();
		jobQueue = new LinkedList<Process>();
		CPU = new LimitedQueue<Process>(1);
		Disk = new Disk();
		
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
