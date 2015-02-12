/**
 * @author 	Magdalene Benson <mebenson12@winona.edu>
 * @date	2/1/2015
 * 
 * @description	A driver class for the CPU Emulator.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client {

	public static void main(String[] args) {
		
		int command;
		Scanner in = new Scanner(System.in);
		Scanner fileInput = new Scanner(System.in);
		ArrayList<Process> processList;
		String dataFile;
		Scheduler scheduler;
		
		System.out.print("\tCPU SCHEDULER\n");
				
		System.out.println("\nSelect A Scheduling Algorithm:");
		System.out.println("\t1. Round Robin (RR)\n" +
							   "\t2. Shortest Job First (SJF)");
			
		// Read user command (make sure it is the correct input format)
		command = in.nextInt();
		if (command > 2 || command < 0) {
			System.out.println("Sorry, you must enter a command of 1 or 2.\n");
			System.exit(0);;
		} else {
			
			switch(command) {
			
				case 1:
					System.out.print("Enter time quantum length:");
					int timeQuantum = in.nextInt();
					
					System.out.print("Enter a filename of jobs data: ");
					dataFile = fileInput.nextLine();
										
					processList = parseInputFile(dataFile);
					
					System.out.println("\n-->\n");
					System.out.println("\nBeginning RR scheduling with time quantum of " + timeQuantum + ".");
					
					break;
					
				case 2:
					System.out.print("Enter a filename of jobs data: ");
					dataFile = fileInput.nextLine();
									
					processList = parseInputFile(dataFile);

					System.out.println("\n-->\n");
					scheduler = new SJFScheduler(processList);
					
					break;
					
				case 0:
				default:
					System.out.println("\nNow exiting.");
					System.exit(0);
					break;
				
			} // End switch
					
		} // End else command

	} // End main
	
	public static ArrayList<Process> parseInputFile(String filePath) {
		
		BufferedReader br = null;
		String line = "";
		String splitBy = " ";
		
		// counter to indicate process Id
		int counter = 1;
		// The process list to return
		ArrayList<Process> processList = new ArrayList<Process>();
	 
		try {
	 
			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {
	 
			    // use space separator
				String[] bursts = line.split(splitBy);
				
				// build the burst arrays
			    ArrayList<Integer> cpuBursts = new ArrayList<Integer>();
			    ArrayList<Integer> ioBursts = new ArrayList<Integer>();
			    
			    for (int i = 0; i < bursts.length; i++) {
			    	
			    	// if the burst is even
			    	if (i % 2 == 0) {
			    		// it's a CPU burst
			    		cpuBursts.add(Integer.parseInt(bursts[i]));
			    	}
			    	// if the burst is odd
			    	else {
			    		// it's an IO burst
			    		ioBursts.add(Integer.parseInt(bursts[i]));
			    	}
			    	
			    } // END FOR LOOP
			    
			    // Create the new Process and add it to the return list
			    Process newProcess = new Process(counter, cpuBursts, ioBursts);
			    processList.add(newProcess);
			    
			    // increment the counter
			    counter++;
			    
			} // END WHILE
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} // END TRY/CATCH
		
		return processList;
		
	} // End parseInputFile().

} // End Client Class
