import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Process Class
 * 
 * @author 	Magdalene Benson <mebenson12@winona.edu>
 * @date	2/4/2015
 *
 */
public class Process {
	
	private int id;
	private List<Integer> cpuBurst;
	private List<Integer> ioBurst;
	//private Iterator cpuIterator;
	//private Iterator ioIterator;
	private int cpuCounter;
	private int ioCounter;
	private int theTime;
	
	/**
	 * Default Constructor
	 * 
	 * @param id - the id of the PCB
	 * @param cpuBurst - the list of CPU bursts
	 * @param ioBurst - the list of IO bursts
	 */
	public Process(int id, List<Integer> cpuBurst, List<Integer> ioBurst) {
		
		this.id = id;
		this.cpuBurst = cpuBurst;
		this.ioBurst = ioBurst;
		//cpuIterator = cpuBurst.iterator();
		//ioIterator = ioBurst.iterator();
		cpuCounter = cpuBurst.size();
		ioCounter = ioBurst.size();
		
	} // End default constructor
	
	/*
	 * Begin Getters 
	 */
	public int getId() {
		return id;
	}
	public List<Integer> getCpuBurst() {
		return cpuBurst;
	}
	public int getNextCpuBurst() {
		return cpuBurst.get(0);
	}
	public List<Integer> getIoBurst() {
		return ioBurst;
	}
	public int getNextIoBurst() {
		return ioBurst.get(0);
	}
	public int getCpuCounter() {
		return cpuCounter;
	}
	public void setCpuCounter(int cpuCounter) {
		this.cpuCounter = cpuCounter;
	}
	public int getIoCounter() {
		return ioCounter;
	}
	public void setIoCounter(int ioCounter) {
		this.ioCounter = ioCounter;
	}

	@Override
	public String toString() {
		
		String text = String.format("\n   PCB %d\n\n\tCPU Bursts: %s\n\tIO Bursts: %s\n\tcpuBurstIndex: %d" +
				"\n\tioBurstIndex: %d\n\tremainingCPUBurstTime: %d\n\tremainingIOBurstTime: %d" +
				"\n\tremainingTicksInTimeslice: %d",
				this.getId(), cpuBurst.toString(), ioBurst.toString(), -1, -1, 0, 0, 0);
		
		return text;
		
	} // End toString().
	
	/**
	 * Runs the CPU burst.
	 */
	public void runCPUBurst() {
		
		
	} // End runCPUBurst().
	
	/**
	 * Runs the IO burst.
	 */
	public void runIOBurst() {
		
		
	} // End runIOBurst().

	public void update(Clock theClock, Process observer) {
		theTime = theClock.getTime();	
	}
	
	
	
	

} // End Process Class
