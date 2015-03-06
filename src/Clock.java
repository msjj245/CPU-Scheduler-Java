/**
 * Clock Class
 * @author 	Magdalene Benson	<mebenson12@winona.edu>
 * @date:	2/4/2015
 * 
 * Mimics a timer or a system clock using only full seconds of int value.
 *
 */

public class Clock {

	private static Clock uniqueInstance = new Clock();
	private int time;
	
	// Protected Constructor
	protected Clock() {
		
		time = 0;
	}
	
	/**
	 * Method:	getInstance()
	 * 	
	 * Returns a unique instance of the Clock.
	 */
	public static Clock getInstance() {
		
		if (uniqueInstance == null) {
			
			uniqueInstance = new Clock();
		}
		
		return uniqueInstance;
	}
	
	/**
	 * Method:	tick()
	 * 
	 * Increments the Clock time.
	 */
	public void tick() {
		
		time++;
	}
	
	
	/**
	 * Method:	getTime()
	 * 
	 * Returns the Clock time property.
	 * @return - time
	 */
	public int getTime() {
		
		return time;
	}
	
} // END CLASS
