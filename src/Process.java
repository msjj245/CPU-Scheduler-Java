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
	private ArrayList<Integer> cpuBurstList;
	private ArrayList<Integer> ioBurstList;
	
	/**
	 * Default Constructor
	 * 
	 * @param id - the id of the PCB
	 * @param cpuBurst - the list of CPU bursts
	 * @param ioBurst - the list of IO bursts
	 */
	public Process(int id, ArrayList<Integer> cpuBurst, ArrayList<Integer> ioBurst) {
		
		this.id = id;
		this.cpuBurstList = cpuBurst;
		this.ioBurstList = ioBurst;
		
	} // End default constructor
	
	/*
	 * Begin Getters/Setters 
	 */
	public int getId() {
		return id;
	}
	public int getNextCpuBurst() {
		
		if (cpuBurstList.iterator().hasNext()) {
			return cpuBurstList.remove(0);
		}
		else {
			return 0;
		}
	}
	public int getNextIoBurst() {
		
		if (ioBurstList.iterator().hasNext()) {
			return ioBurstList.remove(0);
		}
		else {
			return 0;
		}
	}
	public void returnIoBurst(int burst) {
		
		ioBurstList.add(0, burst);
	}
	public void returnCpuBurst(int burst) {
		
		cpuBurstList.add(0, burst);
	}
	public Iterator<Integer> getCpuIterator() {
		return cpuBurstList.iterator();
	}
	public Iterator<Integer> getIoIterator() {
		return ioBurstList.iterator();
	}
	public int getCpuBurstIndex() {
		return cpuBurstList.size();
	}
	public int getIoBurstIndex() {
		return ioBurstList.size();
	}
	public int peekNextCpuBurst() {
		
		if (cpuBurstList.iterator().hasNext()) {
			return cpuBurstList.iterator().next();
		}
		else {
			return 0;
		}
	}
	public int peekNextIoBurst() {
		
		if (ioBurstList.iterator().hasNext()) {
			return ioBurstList.iterator().next();
		}
		else {
			return 0;
		}
	}
	public void setNextCpuBurst(int i) {
		
		cpuBurstList.set(0, i);
	}
	public void setNextIoBurst(int i) {
		
		ioBurstList.set(0,  i);
	}
	
	@Override
	public String toString() {
		
		String text = String.format("\n   PCB %d\n\n\tCPU Bursts: %s\n\tIO Bursts: %s\n\tcpuBurstIndex: %d" +
				"\n\tioBurstIndex: %d\n\tremainingCPUBurstTime: %d\n\tremainingIOBurstTime: %d" +
				"\n\tremainingTicksInTimeslice: %d",
				this.getId(), cpuBurstList.toString(), ioBurstList.toString(), -1, -1, 0, 0, 0);
		
		return text;
		
	} // End toString().

} // End Process Class
