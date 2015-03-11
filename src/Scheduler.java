import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


public abstract class Scheduler {
	
	protected Clock theClock;
	protected Queue<Process> ioWaitQueue;
	protected LinkedList<Process> jobQueue;
	protected LimitedQueue<Process> CPU;
	protected Disk Disk;
	protected boolean contextSwitch;
	
	/**
	 * Default Constructor 
	 */
	public Scheduler(ArrayList<Process> processList) {
		
		theClock = Clock.getInstance();
		ioWaitQueue = new LinkedList<Process>();
		jobQueue = new LinkedList<Process>();
		CPU = new LimitedQueue<Process>(1);
		Disk = new Disk();
		
		contextSwitch = false;
		
	} // End Default Constructor
	
	/**
	 * Run the scheduler
	 */
	public abstract void run();
	
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
	
	/**
	 * Uses Iterator to traverse through the readyQueue.
	 * @return String of readyQueue id's.
	 */
	public abstract String getReadyQueueContents();
	
	/**
	 * Uses Iterator to traverse through the jobQueue.
	 * @return String of jobQueue id's.
	 */
	public String getJobQueueContents() {
		
		String ret = "";
		Iterator<Process> jobQueueIterator = jobQueue.iterator();
		while (jobQueueIterator.hasNext()) {
			ret += jobQueueIterator.next().getId() + " ";
		}
		return ret;
				
	} // End getJobQueueContents().
	
	/**
	 * Uses Iterator to traverse through the diskQueue.
	 * @return String of diskQueue id's.
	 */
	public String getDiskQueueContents() {
		
		String ret = "";
		Iterator<Process> diskIterator = Disk.iterator();
		while (diskIterator.hasNext()) {
			ret += diskIterator.next().getId() + " ";
		}
		return ret;
				
	} // End getDiskQueueContents().
	
	/**
	 * Uses Iterator to traverse through the ioWaitQueue.
	 * @return String of ioWaitQueue id's.
	 */
	public String getIoWaitQueueContents() {
		
		String ret = "";
		Iterator<Process> IoWaitQueueIterator = ioWaitQueue.iterator();
		while (IoWaitQueueIterator.hasNext()) {
			ret += IoWaitQueueIterator.next().getId() + " ";
		}
		return ret;
				
	} // End getIoWaitQueueContents().
	
	/**
	 * Prints the contents of the CPU.
	 */
	public void getCpuContents() {
		
		if (!CPU.isEmpty()) {
			System.out.printf("\t%-15s %3d\n", "CPU:", CPU.peek().getId());
		}
		else {
			System.out.printf("\t%-15s\n", "CPU:");
		}
		
	} // End getCpuContents().
	
} // End Scheduler Class
