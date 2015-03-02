import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class SJF extends Scheduler {
	
	private Clock theClock;
	
	/**
	 * Default Constructor
	 * @param processList
	 */
	public SJF(ArrayList<Process> processList) {
		
		super(processList);
		
		// Assign the comparator for the job time
		jobQueue = new PriorityQueue<Process>(jobTimeComparator);
		
		theClock = Clock.getInstance();
		loadProcesses(processList);
		
		System.out.println("\nTimer is 0-based\n");
		System.out.println("/////////////////////////////////");
		
		contextSwitch();
	}
	
	/**
	 * Assigns a comparator for the totalJobTime of a Process
	 */
    public static Comparator<Process> jobTimeComparator = new Comparator<Process>(){
         
        @Override
        public int compare(Process p1, Process p2) {
        	
        	int p1Burst = p1.peekNextCpuBurst();
        	int p2Burst = p2.peekNextCpuBurst();
        	
        	if (p1Burst == p2Burst) {
        		
        		if (p1.getId() > p2.getId()) {
        			
        			return 1;
        		}
        		else {
        			
        			return -1;
        		}
        	}
        	else if (p1Burst > p2Burst) {
        	
                return 1;
        	}
        	else {
        		return -1;
        	}
        }
        
    }; // End jobTimeComparator().

	@Override
	/**
	 * Begin SJF Algorithm
	 */
	public void contextSwitch() {
		
		// while the ready queue still has processes to run
		while(readyQueue.iterator().hasNext()) {
			
			// (if) the CPU is empty, fill it from the ready queue
			// (else) move the PCB from the CPU to Disk
			if (CPU.isEmpty()) {
				
				CPU.add(readyQueue.remove());
				
			}
			else if (!Disk.isEmpty() && (readyQueue.size() < readyQueue.getLimit()) 
					&& ioWaitQueue.isEmpty() && CPU.isEmpty()) {
				 
				CPU.add(Disk.remove());
			}
			printState();
			run();
			if (!ioWaitQueue.isEmpty() && (readyQueue.size() < readyQueue.getLimit())) {
				
				readyQueue.add(ioWaitQueue.remove());
			}
			else if (ioWaitQueue.isEmpty() && !jobQueue.isEmpty() 
					&& (readyQueue.size() < readyQueue.getLimit())) {
				
				readyQueue.add(jobQueue.remove());
			}
			
		}

	} // End begin().
	
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
				
	} // End getReadyQueueContents().
	
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
		
		// System.out.println("JobQueue Order: " + jobQueue.toString());
		
		// load the first three processes into the readyQueue
		for (int i = 0; i < readyQueue.getLimit(); i++) {
			
			readyQueue.add(jobQueue.remove());
		}
		
	} // End loadProcesses().

	@Override
	/**
	 * Prints the current state of the Scheduler.
	 */
	public void printState() {
		
		System.out.printf("\nTime = %d\n\tCPU: %d\n\tJob Queue: %s\n\tReadyQueue: %s\n\t" +
						"Disk Queue: %s\n\tIO Wait Queue: %s\n" +
						"", theClock.getTime(), CPU.peek().getId(),
						getJobQueueContents(), getReadyQueueContents(),
						getDiskQueueContents(), getIoWaitQueueContents() );
		
	} // End printState().
	
	/**
	 * Run the processes in the CPU and Disk
	 */
	private void run() {
		
		if ( !CPU.isEmpty() ) {
			
			Process cpuProcess = CPU.remove();
			int cpuBurst = cpuProcess.getNextCpuBurst();
			
			Process ioProcess = null;
			int ioBurst = 0;
			
			/*
			 * Get the process on the disk if there is one, to decrement that too
			 */
			if ( !Disk.isEmpty() ) {
				
				ioProcess = Disk.remove();
				ioBurst = ioProcess.getNextIoBurst();
			}
			
			/*
			 * Let the CPU burst guide the clock ticks
			 */
			while (cpuBurst > 0) {
				
				theClock.tick();
				
				if (ioProcess != null) {
					
					if (ioBurst > 0) {
						
						ioBurst--;
					}
					else if (ioBurst == 0) {
						
						/*
						 * The Disk process is done but the CPU is still ticking the clock.
						 * Move on to the next process in the Disk.
						 */
						if ( !Disk.isEmpty() ) {
							
							ioWaitQueue.add(ioProcess);
							if (Disk.iterator().hasNext()) {
								
								ioProcess = Disk.remove();
								ioBurst = ioProcess.getNextIoBurst();
							}
							// ELSE omitted intentionally
						}
						// ELSE omitted intentionally
					}
				}
				// ELSE omitted intentionally
				
				cpuBurst--;
			}
			// END while
			
			/*
			 * Disk process didn't finish running io burst,
			 * return it to the Disk head.
			 */
			if (ioBurst > 0) {
				
				ioProcess.returnIoBurst(ioBurst);
				Disk.addFirst(ioProcess);
			}
			// ELSE omitted intentionally
			
			/*
			 * No more CPU bursts to run, delete/remove the process as finished.
			 */
			if (cpuProcess.getCpuBurstIndex() == 0) {
				
				cpuProcess = null;
			}
			else {
				
				Disk.add(cpuProcess);
			}
		
		}
		// ELSE omitted intentionally
		
	} // End run().

} // End SJF Class
