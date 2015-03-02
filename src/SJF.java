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
		
		loadProcesses(processList);
		theClock = Clock.getInstance();
		
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
        	
        	int ret = 0;
        	
        	if (p1.getNextCpuBurst() == p2.getNextCpuBurst()) {
        		
        		ret = (p1.getId() - p2.getId());
        	}
        	else {
        	
                ret = (int) (p1.getNextCpuBurst() - p2.getNextCpuBurst());
        	}
        	return ret;
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
			else {
				
				Disk.add(CPU.remove());
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
			
			if ( !Disk.isEmpty() ) {
				
				ioProcess = Disk.remove();
				ioBurst = ioProcess.getNextIoBurst();
			}
			
			while (cpuBurst > 0) {
				
				theClock.tick();
				
				if (ioProcess != null && ioBurst > 0) {
					
					ioBurst--;
				}
				else {
					
					// get the next ioProcess
					if ( !Disk.isEmpty() ) {
						
					}
				}
				cpuBurst--;
			}
			
			if (ioBurst > 0) {
				
				ioProcess.returnIoBurst(ioBurst);
				Disk.addFirst(ioProcess);
			}
			
			if (cpuProcess.getCpuBurstIndex() == 0) {
				
				cpuProcess = null;
			}
			else {
				
				Disk.add(cpuProcess);
			}
		
		}
		
	} // End run().

} // End SJF Class
