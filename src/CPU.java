/**
 * Class: CPU
 * @author 	Magdalene Benson	<mebenson12@winona.edu>
 * @date	02/12/2015
 *
 */

@SuppressWarnings("serial")
public class CPU extends LimitedQueue<Process> {
	
	private Clock theClock = Clock.getInstance();
	
	/**
	 * Default Constructor
	 */
	public CPU(int limit) {
		super(limit);
	}
	
	public void run() {
		
		Process myProcess = this.remove();
		int thisBurst = myProcess.getCpuBurst().remove(0);
		
		for (int i = 0; i < thisBurst; i++) {
			theClock.tick();
		}
		
		// put the process back if it has more CPU bursts
		if (myProcess.getCpuCounter() > 0) {
			this.add(myProcess);
		}
		else {
			myProcess = null;
		}
		
	} // End load()
	
	
	
} // End CPU Class
