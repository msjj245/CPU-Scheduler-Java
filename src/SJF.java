import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class SJF extends Scheduler {
	
	boolean state;
	
	/**
	 * Default Constructor
	 * @param processList
	 */
	public SJF(ArrayList<Process> processList) {
		
		super(processList);
		
		// Assign the comparator for the job time
		jobQueue = new PriorityQueue<Process>(jobTimeComparator);
		
		loadProcesses(processList);
		
		System.out.println("\nTimer is 0-based\n");
		System.out.println("/////////////////////////////////");
		
		begin();
	}
	
	/**
	 * Assigns a comparator for the totalJobTime of a Process
	 */
    public static Comparator<Process> jobTimeComparator = new Comparator<Process>(){
         
        @Override
        public int compare(Process p1, Process p2) {
           return (int) (p1.getNextCpuBurst() - p2.getNextCpuBurst());
        }
        
    }; // End jobTimeComparator().

	@Override
	/**
	 * Begin SJF Algorithm
	 */
	public void begin() {
		
		// while the ready queue still has processes to run
		while(readyQueue.iterator().hasNext()) {
			
			// (if) the CPU is empty, fill it from the ready queue
			// (else) move the PCB from the CPU to Disk
			if (CPU.isEmpty()) {
				
				// from the ready queue
				CPU.add(readyQueue.remove());
				
				// (if) the wait queue has something waiting
				// and the ready queue has room
				// add it to the ready queue
				// (else if) the wait queue is empty and the job queue is not
				// and the ready queue has room, add from the job queue to the ready queue
				if (!ioWaitQueue.isEmpty() && (readyQueue.size() < readyQueue.getLimit())) {
					
					readyQueue.add(ioWaitQueue.remove());
				}
				else if (ioWaitQueue.isEmpty() && !jobQueue.isEmpty() 
						&& (readyQueue.size() < readyQueue.getLimit())) {
					
					readyQueue.add(jobQueue.remove());
				}
				
			}
			else {
				
				diskQueue.add(CPU.remove());
			}
			printState();
			run();
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
		// don't include the first one since it is being added to the CPU
		ret = ret.substring(2);
		return ret;
				
	} // End getReadyQueueContents().
	
	/**
	 * Uses Iterator to traverse through the diskQueue.
	 * @return String of diskQueue id's.
	 */
	public String getDiskQueueContents() {
		
		String ret = "";
		Iterator<Process> diskQueueIterator = diskQueue.iterator();
		while (diskQueueIterator.hasNext()) {
			ret += diskQueueIterator.next().getId() + " ";
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
	
	private void run() {
		
		if (!CPU.isEmpty()) {
			CPU.run();
			if (!CPU.isEmpty()) {
				
				diskQueue.add(CPU.remove());
			}
			if ( (readyQueue.size() != readyQueue.getLimit()) && !jobQueue.isEmpty()) {
				readyQueue.add(jobQueue.remove());
			}
		}
		if (!diskQueue.isEmpty()) {
			// diskQueue.run();
		}
		
	} // End run().

} // End SJF Class
