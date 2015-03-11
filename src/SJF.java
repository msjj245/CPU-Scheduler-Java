import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class SJF extends Scheduler {
	
	private final int READY_QUEUE_SIZE = 3;
	private PriorityQueue<Process> readyQueue;
	
	/**
	 * Default Constructor
	 * @param processList
	 */
	public SJF(ArrayList<Process> processList) {
		
		super(processList);
		
		/*
		 * Assigns the readyQueue a burst time comparator
		 */
		readyQueue = new PriorityQueue<Process>(burstTimeComparator);
		
		loadProcesses(processList);
		
		System.out.println("\nTimer is 0-based\n");
		System.out.println("/////////////////////////////////");
		
		run();
	}
	
	/**
	 * Assigns a comparator for the next CPU burst of a Process.
	 * If the burst time is the same, compare the id for order.
	 * ---Smaller id is a greater priority.
	 */
    public static Comparator<Process> burstTimeComparator = new Comparator<Process>(){
         
        @Override
        public int compare(Process p1, Process p2) {
        	
        	if (p1.peekNextCpuBurst() == p2.peekNextCpuBurst()) {
        		
        		if (p1.getId() > p2.getId()) {
        			
        			return 1;
        		}
        		else {
        			
        			return -1;
        		}
        	}
        	else if (p1.peekNextCpuBurst() > p2.peekNextCpuBurst()) {
        	
                return 1;
        	}
        	else {
        		
        		return -1;
        	}
        }
        
    }; // End jobTimeComparator().

    /**
	 * Run the Scheduler.
	 */
	public void run() {
		
		/*
		 * While there is still something to run from the readyQueue or already in the CPU.
		 */
		while (readyQueue.iterator().hasNext() || !CPU.isEmpty()) {
			
			/*
			 * Set up the CPU with the first process to get the ball rolling..
			 */
			if (CPU.isEmpty()) {
				
				CPU.add(readyQueue.remove());
			}
			
			printState();
			while (!contextSwitch) {
				
				Process cpuProcess = null;
				int cpuBurst = 0;
				
				if ( !CPU.isEmpty() ) {
					
					cpuProcess = CPU.remove();
					cpuBurst = cpuProcess.getNextCpuBurst();		
				}
				// ELSE omitted intentionally
				
				/*
				 * Let the cpuBurst guide the clock ticks
				 */
				while (cpuBurst > 0) {
					
					theClock.tick();
					cpuBurst--;
					
					if (!Disk.isEmpty()) {
						
						Disk.decrement();
						
						while (Disk.isCompleted()) {
							
							ioWaitQueue.add(Disk.remove());
						}
					}
					// ELSE omitted intentionally
				}
				// End WHILE
				
				if (cpuProcess.getCpuBurstIndex() == 0) {
					
					cpuProcess = null;
				}
				else {
					
					Disk.add(cpuProcess);
				}
				
				contextSwitch = true;
			}
			// End WHILE
			
			
			/*
			 * Refill the readyQueue from the ioWaitQueue first, then from the jobQueue.
			 */
			while (ioWaitQueue.iterator().hasNext() && (readyQueue.size() < READY_QUEUE_SIZE)) {
				
				readyQueue.add(ioWaitQueue.remove());
			}
			
			if (!jobQueue.isEmpty() && (readyQueue.size() < READY_QUEUE_SIZE)) {
				
				readyQueue.add(jobQueue.remove());
			}
			// ELSE omitted intentionally
			
			if ( CPU.isEmpty() && !readyQueue.isEmpty() ) {
				
				CPU.add(readyQueue.remove());
				contextSwitch = false;
			}
			// ELSE omitted intentionally
			
		}
		// END while CPU not empty
		
		/*
		 * Print the final empty state.
		 */
		printState();
		
	} // End run().
	
	/**
	 * Uses Iterator to traverse through the readyQueue.
	 * @return String of readyQueue id's.
	 */
	public String getReadyQueueContents() {
		
		String ret = "";
		Iterator<Process> readyQueueIterator = readyQueue.iterator();
		while (readyQueueIterator.hasNext()) {
			ret += readyQueueIterator.next().getId() + " ";
		}
		return ret;
				
	} // End getReadyQueueContents().
	
	/**
	 * Loads all processes into the job queue,
	 * then 3 processes to the ready queue.
	 * 
	 * @param processList
	 */
	public void loadProcesses(ArrayList<Process> processList) {
		
		System.out.println("\nInitial Job Queue:  =======================");
		
		/*
		 *  load the processes into the jobQueue
		 */
		int processListSize = processList.size();
		
		for (int j = 0; j < processListSize; j++) {
			
			Process thisProcess = processList.remove(0);
			System.out.println(thisProcess.toString());
			jobQueue.add(thisProcess);
		}
				
		/*
		 * load the first three processes into the readyQueue
		 */
		for (int i = 0; i < READY_QUEUE_SIZE; i++) {
			
			readyQueue.add(jobQueue.remove());
		}
		
	} // End loadProcesses().

	@Override
	/**
	 * Prints the current state of the Scheduler.
	 */
	public void printState() {
		
		System.out.printf("\nTime = %d\n", theClock.getTime());
		getCpuContents();
		System.out.printf("\t%-15s %4s\n", "Job Queue:", getJobQueueContents());
		System.out.printf("\t%-15s %4s\n", "Ready Queue:", getReadyQueueContents());
		System.out.printf("\t%-15s %4s\n", "Disk Queue:", getDiskQueueContents());
		System.out.printf("\t%-15s %4s\n", "IOWait Queue:", getIoWaitQueueContents());			
		
	} // End printState().
	
} // End SJF Class
