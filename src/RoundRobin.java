import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


public class RoundRobin extends Scheduler {

	private final int READY_QUEUE_SIZE = 3;
	private int timeQuantum;
	private boolean contextSwitch;
	private Queue<Process> readyQueue;
	
	/**
	 * Default Constructor
	 * 
	 * @param processList
	 * @param timeQuantum
	 */
	public RoundRobin(ArrayList<Process> processList, int timeQuantum) {
		
		super(processList);
		
		/*
		 * Make sure the user input a correct time quantum.
		 */
		if (timeQuantum > 0) {
			
			this.timeQuantum = timeQuantum;
		}
		else {
			
			System.out.println("CANNOT PROCESS WITH A TIME QUANTUM OF 0.");
			System.exit(0);
		}
		
		/*
		 * Assigns the readyQueue an order comparator for the ID (FIFO)
		 */
		readyQueue = new LinkedList<Process>();
		
		loadProcesses(processList);
		
		System.out.println("\nTimer is 0-based\n");
		System.out.println("/////////////////////////////////");
		
		contextSwitch = false;
		run();
	}
	
	/**
	 * Run the RR Scheduler
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
				int timeQuantumCounter = timeQuantum; 
				
				/*
				 * Let the time quantum guide the clock ticks
				 */
				while (timeQuantumCounter > 0) {
					
					if ( !CPU.isEmpty() ) {
						
						cpuProcess = CPU.remove();
						cpuBurst = cpuProcess.getNextCpuBurst();		
					}
					else if (cpuProcess == null) {
						/*
						 * No more processes, end.
						 */
						break;
					}
					
					theClock.tick();
					timeQuantumCounter--;
					cpuBurst--;
					
					if (!Disk.isEmpty()) {
						
						Disk.decrement();
						
						while (Disk.isCompleted()) {
							
							ioWaitQueue.add(Disk.remove());
						}
					}
					// ELSE omitted intentionally
					
					if ( cpuBurst > 0 && timeQuantumCounter == 0 ) {
						
						cpuProcess.returnCpuBurst(cpuBurst);
						readyQueue.add(cpuProcess);
					}
					else if (cpuBurst == 0 && timeQuantumCounter > 0) {
						
						if (cpuProcess.getCpuBurstIndex() > 0) {
							
							Disk.add(cpuProcess);
							break;
						}
						else {
							
							cpuProcess = null;
							break;
						}
					}
					else if (cpuBurst == 0 && timeQuantumCounter == 0) {
						
						if (cpuProcess.getCpuBurstIndex() > 0) {
							
							Disk.add(cpuProcess);
						}
						else {
							
							cpuProcess = null;
							break;
						}
					}
				}
				// END while timeQuantumCounter
				
				contextSwitch = true;
			}
			// END while contextSwitch false
			
			
			/*
			 * Refill the readyQueue from the ioWaitQueue first, next from the jobQueue.
			 */
			while (ioWaitQueue.iterator().hasNext() && (readyQueue.size() < READY_QUEUE_SIZE)) {
				
				readyQueue.add(ioWaitQueue.remove());
			}
			// End WHILE
			
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
		
	} // END run().

	@Override
	public void printState() {

		System.out.printf("\nTime = %d\n", theClock.getTime());
		getCpuContents();
		System.out.printf("\t%-15s %4s\n", "Job Queue:", getJobQueueContents());
		System.out.printf("\t%-15s %4s\n", "Ready Queue:", getReadyQueueContents());
		System.out.printf("\t%-15s %4s\n", "Disk Queue:", getDiskQueueContents());
		System.out.printf("\t%-15s %4s\n", "IOWait Queue:", getIoWaitQueueContents());	
	}
	
	@Override
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
		
	} // END loadProcesses().

	@Override
	public String getReadyQueueContents() {
		
		String ret = "";
		Iterator<Process> readyQueueIterator = readyQueue.iterator();
		while (readyQueueIterator.hasNext()) {
			ret += readyQueueIterator.next().getId() + " ";
		}
		return ret;
		
	} // END getReadyQueueContents().

} // END RoundRobin Scheduler Class
