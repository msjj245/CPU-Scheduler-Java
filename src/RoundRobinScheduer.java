import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class RoundRobinScheduer extends Scheduler {

	public int quantumTime;
	public Queue<Process> jobQueue;
	
	public RoundRobinScheduer(ArrayList<Process> processList, int quantumTime) {
		super(processList);
		
		jobQueue = new LinkedList<Process>();
		this.quantumTime = quantumTime;
	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadProcesses(ArrayList<Process> processList) {
		// TODO Auto-generated method stub
		
	}

}
