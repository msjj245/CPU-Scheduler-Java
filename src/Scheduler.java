import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public abstract class Scheduler {
	
	protected Clock theClock;
	protected LimitedQueue<Process> readyQueue;
	protected Queue<Process> ioWaitQueue;
	protected PriorityQueue<Process> jobQueue;
	protected CPU CPU;
	protected Disk diskQueue;
	
	/**
	 * Default Constructor
	 */
	public Scheduler(ArrayList<Process> processList) {
		
		theClock = Clock.getInstance();
		readyQueue = new LimitedQueue<Process>(3);
		ioWaitQueue = new LinkedList<Process>();
		CPU = new CPU(1);
		diskQueue = new Disk();
		
		
	} // End Default Constructor
	
	/**
	 * Begin scheduling algorithm
	 */
	public abstract void begin();
	
	/**
	 * Loads all processes into the job queue,
	 * then 3 processes to the ready queue.
	 * 
	 * @param processList
	 */
	public void loadProcesses(ArrayList<Process> processList) {
		
		System.out.println("\nInitial Job Queue:  =======================");
		
		// load the processes into the jobQueue
		int processListSize = processList.size();
		
		for (int j = 0; j < processListSize; j++) {
			
			Process thisProcess = processList.remove(0);
			theClock.addObserver(thisProcess);
			
			System.out.println(thisProcess.toString());
			jobQueue.add(thisProcess);
		}
		
		// load the first three processes into the readyQueue
		for (int i = 0; i < readyQueue.getLimit(); i++) {
			
			readyQueue.add(jobQueue.remove());
		}
		
	} // End loadProcesses().
	
	/**
	 * Prints the current state of the CPU Scheduler.
	 */
	public abstract void printState();
	
	
} // End Scheduler Class
