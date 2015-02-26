import java.util.LinkedList;

@SuppressWarnings("serial")
public class Disk extends LinkedList<Process> {

	private Clock theClock;

	public Disk() {
		theClock  = Clock.getInstance();
	}
	
	public void run() {
		
		
	} // End run()

} // End Disk Class
