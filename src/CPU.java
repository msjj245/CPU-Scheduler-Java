/**
 * Class: CPU
 * @author 	Magdalene Benson	<mebenson12@winona.edu>
 * @date	02/12/2015
 *
 */

@SuppressWarnings("serial")
public class CPU extends LimitedQueue<Process> {
	
	/**
	 * Default Constructor
	 */
	public CPU(int limit) {
		
		super(limit);
	}
	
} // End CPU Class
