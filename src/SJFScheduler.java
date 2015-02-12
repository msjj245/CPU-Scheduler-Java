import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


public class SJFScheduler extends Scheduler {
	
	private boolean lockFlag;
	private PriorityQueue<Process> jobQueue;

	public SJFScheduler(ArrayList<Process> processList) {
		
		super(processList);
		jobQueue = new PriorityQueue<Process>(jobTimeComparator);
		loadProcesses(processList);
		begin();
	}

	@Override
	public void begin() {
		
		// 1.
		// Load a Process from the ready queue
		// into the CPU and run the CPU burst
		// and move a Process from the jobQueue
		// to the end of the readyQueue.
		while ( lockFlag ) {
			
			printStatus();
			lockFlag = false;
			
		}
		
		// 2.
		// When the CPU burst is finished,
		// load the process into the diskQueue to perform IO burst
		// and load the next Process into the CPU.
		
		// 3.
		// When the IO burst is finished,
		// load the process into the IO waitQueue
		// until the CPU is open to run the next CPU burst
		// of this Process.
		
		// 4.
		// When the Process is completely finished remove from the Queues.
		
	}
	
	// Comparator Process class implementation
    public static Comparator<Process> jobTimeComparator = new Comparator<Process>(){
         
        @Override
        public int compare(Process p1, Process p2) {
            return (int) (p1.getJobTime() - p2.getJobTime());
        }
    };

	@Override
	public void loadProcesses(ArrayList<Process> processList) {
		
		// load the processes into the jobQueue
		// and link them as they go.
		int processListSize = processList.size();
		for (int j = 0; j < processListSize; j++) {
			
			Process current = processList.remove(0);
			
			if (processList.size() != 0) {
				current.setNext(processList.get(0));
			}
			jobQueue.add(current);
		}
		
		// load the first three processes into the readyQueue
		for (int i = 0; i < readyQueue.getLimit(); i++) {
			
			readyQueue.add(jobQueue.remove());
		}
		
	} // End

} // End SJFScheduler Class
