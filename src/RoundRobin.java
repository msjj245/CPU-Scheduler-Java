import java.util.ArrayList;
import java.util.Comparator;


public class RoundRobin extends Scheduler {

	public RoundRobin(ArrayList<Process> processList) {
		super(processList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void contextSwitch() {
		// TODO Auto-generated method stub

	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Assigns a comparator for the totalJobTime of a Process
	 */
    public static Comparator<Process> orderComparator = new Comparator<Process>(){
         
        @Override
        public int compare(Process p1, Process p2) {
            return (int) (p1.getId() - p2.getId());
        }
    };

	@Override
	public void loadProcesses(ArrayList<Process> processList) {
		// TODO Auto-generated method stub
		
	}

}
