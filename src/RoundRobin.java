import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;


public class RoundRobin extends Scheduler {

	private final int READY_QUEUE_SIZE = 3;
	private int timeQuantum;
	private boolean contextSwitch;
	
	/*
	 * Custom Constructor
	 */
	public RoundRobin(ArrayList<Process> processList, int timeQuantum) {
		
		super(processList);
		
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
		readyQueue = new PriorityQueue<Process>(orderComparator);
		
		loadProcesses(processList);
		
		System.out.println("\nTimer is 0-based\n");
		System.out.println("/////////////////////////////////");
		
		contextSwitch = false;
		run();
	}
	
	/**
	 * Run the Scheduler
	 */
	private void run() {
		
		/*
		 * While there is still something to run from the readyQueue.
		 */
		while (readyQueue.iterator().hasNext()) {
			
			/*
			 * Set up the CPU with the first process to get the ball rolling..
			 */
			if (CPU.isEmpty()) {
				
				CPU.add(readyQueue.remove());
			}
			
			while (!contextSwitch) {
				
				Process cpuProcess = null;
				int cpuBurst = 0;
				int timeQuantumCounter = timeQuantum; 
				
				if ( !CPU.isEmpty() ) {
					
					cpuProcess = CPU.remove();
					cpuBurst = cpuProcess.getNextCpuBurst();		
				}
				else if (CPU.isEmpty() && readyQueue.iterator().hasNext()) {
					
					cpuProcess = readyQueue.remove();
					cpuBurst = cpuProcess.getNextCpuBurst();
				}
				
				/*
				 * Let the time quantum guide the clock ticks
				 */
				while (timeQuantumCounter > 0) {
					
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
				}
				// END while timeQuantumCounter
				
				contextSwitch = true;
			}
			// END while contextSwitch false
		}
		// END while CPU not empty
		
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
	
	/**
	 * Assigns a comparator for the FIFO of a Process in the readyQueue
	 */
    public static Comparator<Process> orderComparator = new Comparator<Process>(){
         
        @Override
        public int compare(Process p1, Process p2) {
            return (int) (p1.getId() - p2.getId());
        }
    };

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

	@Override
	public void contextSwitch() {
		// TODO Auto-generated method stub
		
	}

} // END RoundRobin Scheduler Class
