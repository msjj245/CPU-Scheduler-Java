import java.util.ArrayList;
import java.util.Iterator;

/**
 * Process Class
 * 
 * @author 	Magdalene Benson <mebenson12@winona.edu>
 * @date	2/4/2015
 *
 */
public class Process {
	
	private int id;
	private ArrayList<Integer> cpuBurst;
	private ArrayList<Integer> ioBurst;
	private Iterator cpuIndex;
	private Iterator ioIndex;
	private int cpuCounter;
	private int ioCounter;
	private Process next;
	
	/**
	 * Default Process Constructor
	 * 
	 * @param id - the id of the PCB
	 * @param cpuBurst - the list of CPU bursts
	 * @param ioBurst - the list of IO bursts
	 * @param next - pointer to the next Process
	 */
	public Process(int id, ArrayList<Integer> cpuBurst, ArrayList<Integer> ioBurst) {
		
		this.id = id;
		this.cpuBurst = cpuBurst;
		this.ioBurst = ioBurst;
		cpuIndex = cpuBurst.iterator();
		ioIndex = ioBurst.iterator();
		cpuCounter = cpuBurst.size();
		ioCounter = ioBurst.size();
		next = null;
		
	} // End default constructor
	
	/*
	 * Begin Getters 
	 */
	public int getId() {
		return id;
	}
	public ArrayList<Integer> getCpuBurst() {
		return cpuBurst;
	}
	public ArrayList<Integer> getIoBurst() {
		return ioBurst;
	}
	public Iterator getCpuIndex() {
		return cpuIndex;
	}
	public Iterator getIoIndex() {
		return ioIndex;
	}
	public Process getNext() {
		return next;
	}
	
	/*
	 * Begin Setters
	 */
	public void setNext(Process next) {
		this.next = next;
	}

	/*
	 * Prints out a Process block data.
	 */
	@Override
	public String toString() {
		return "\nProcess [id=" + id + ", cpuBurst=" + cpuBurst + ", ioBurst="
				+ ioBurst + ", cpuIndex=" + cpuIndex + ", ioIndex=" + ioIndex
				+ ", next=" + next + "]";
	}

	public int getJobTime() {
		
		int jobTime = 0;
		
		for (int i = 0; i < cpuBurst.size(); i++) {
			
			jobTime += cpuBurst.get(i);
		}
		for (int j = 0; j < ioBurst.size(); j++) {
			
			jobTime += ioBurst.get(j);
		}
		System.out.println(this.id + " JobTime: " + jobTime);
		return jobTime;
	}

} // End Process Class
