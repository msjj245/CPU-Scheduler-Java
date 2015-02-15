import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public class Scheduler {
	
	protected Clock theClock;
	public LimitedQueue<Process> readyQueue;
	public Queue<Process> diskQueue;
	public Queue<Process> ioQueue;
	public LimitedQueue<Process> CPU;
	private PriorityQueue<Process> jobQueue;
	
	/**
	 * Default Constructor
	 */
	public Scheduler(ArrayList<Process> processList, String type) {
		
		theClock = new Clock();
		readyQueue = new LimitedQueue<Process>(3);
		diskQueue = new LinkedList<Process>();
		ioQueue = new LinkedList<Process>();
		CPU = new LimitedQueue<Process>(1);
		
		switch (type) {
		
			case "SJF":
				jobQueue = new PriorityQueue<Process>(jobTimeComparator);
				// beginSJF();
				break;
				
			case "RoundRobin":
				jobQueue = new PriorityQueue<Process>(orderComparator);
				// beginRoundRobin();
				break;
				
			default:
				break;
		
		}
		
	} // End Default Constructor
	
	/**
	 * Begin processing by SJF algorithm
	 */
	public void beginSJF() {
		
		
	}
	
	/**
	 * Begin processing by Round Robin algorithm
	 */
	public void beginRoundRobin() {
		
	}
	
	
	public void incrementClock(int i) {
		
		for (int j = 0; j > i; j++) {
			theClock.tick();
		}
	}
	
	/**
	 * Loads all processes into the job queue
	 * 
	 * @param processList
	 */
	public void loadProcesses(ArrayList<Process> processList) {
		
		// load the processes into the jobQueue.
		int processListSize = processList.size();
		for (int j = 0; j < processListSize; j++) {
			
			jobQueue.add(processList.remove(0));
		}
		
		// load the first three processes into the readyQueue
		for (int i = 0; i < readyQueue.getLimit(); i++) {
			
			readyQueue.add(jobQueue.remove());
		}
		
	} // End loadProcesses
	
	/**
	 * Assigns a comparator for the totalJobTime of a Process
	 */
    public static Comparator<Process> jobTimeComparator = new Comparator<Process>(){
         
        @Override
        public int compare(Process p1, Process p2) {
            return (int) (p1.getTotalJobTime() - p2.getTotalJobTime());
        }
    };
    
    /**
	 * Assigns a comparator for the totalJobTime of a Process
	 */
    public static Comparator<Process> orderComparator = new Comparator<Process>(){
         
        @Override
        public int compare(Process p1, Process p2) {
            return (int) (p1.getId() - p2.getId());
        }
    };
	
	
} // End Scheduler Class
