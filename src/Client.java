/**
 * @author 	Magdalene Benson <mebenson12@winona.edu>
 * @date	2/1/2015
 * 
 * @description	A driver class for the CPU Emulator.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws IOException {
		
		int command;
		Scanner input = new Scanner(System.in);
		ArrayList<Process> processList;
		String dataFile;
		@SuppressWarnings("unused")
		Scheduler scheduler;
		
		System.out.print("\tCPU SCHEDULER\n");
				
		System.out.println("\nSelect Scheduling Algorithm:");
		System.out.println("\t1. Round Robin (RR)\n" +
							   "\t2. Shortest Job First (SJF)");
			
		/*
		 * Read user command (make sure it is the correct input format)
		 */
		command = input.nextInt();
		input.nextLine();
		
		if (command > 2 || command < 0) {
			
			System.out.println("Sorry, you must enter a command of 1 or 2.\n");
			System.exit(0);
		} else {
			
			switch(command) {
			
				case 1:
					
					int timeQuantum = 0;
					System.out.print("Enter a filename of jobs data: ");
					// dataFile = input.nextLine();
					
					processList = parseInputFile("jobs-testdata2.txt");
					
					System.out.print("Enter time quantum length:");
					timeQuantum = 2; // input.nextInt();
					
					System.out.println("\n-->\nUsing RoundRobin Scheduling with a time quantum of " + timeQuantum + "\n");
					scheduler = new RoundRobin(processList, timeQuantum);
					
					break;
					
				case 2:
					
					System.out.println("\n-->\nUsing SJF Scheduling\n\n");
					System.out.print("Enter filename of jobs data: ");
					dataFile = input.nextLine();
					
					processList = parseInputFile(dataFile);
					scheduler = new SJF(processList);
					
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
