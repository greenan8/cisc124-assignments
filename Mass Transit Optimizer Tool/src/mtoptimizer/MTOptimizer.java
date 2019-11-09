/*------------------------------------------------------------------------
Author: 		Andrew Greenan
Student #:   	20004588
Description: 	This program is designed to read in data of passenger
				usage for different forms of public transport in Toronto
				and also data on the vehicles avaliable for operation.
				It creates objects of these passengers and utilizes
				inheritence when creating objects of different vehicles.
				
				This class is the main driver and reads in the data
				creates the objects, creates an error log for passenger
				data, determines hourly demands, and determines optimal
				fleets
------------------------------------------------------------------------*/


package mtoptimizer;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class MTOptimizer {
	
	//arraylists for each set of objects and the passenger error log
	static ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	static ArrayList<String> passengerErrorLog = new ArrayList<String>();
	static ArrayList<Subway> subways = new ArrayList<Subway>();
	static ArrayList<Gotrain> gotrains = new ArrayList<Gotrain>();
	static ArrayList<Streetcar> streetcars = new ArrayList<Streetcar>();
	static ArrayList<Bus> buses = new ArrayList<Bus>();
	static ArrayList<Gobus> gobuses = new ArrayList<Gobus>();
	
	
	//Hourly demand arrays
	static double[] subwayDemand = new double[24];
	static double[] gotrainDemand = new double[24];
	static double[] streetcarDemand = new double[24];
	static double[] busDemand = new double[24];
	static double[] gobusDemand = new double[24];
	static double[][] allHourly = {subwayDemand, gotrainDemand, streetcarDemand, busDemand, gobusDemand};
	
	public static void main(String[] args) {
		
		//read each file and create objects for each
		fileReader("inputfiles/ridership.txt", 'P');
		fileReader("inputfiles/subways.txt", 'S');
		fileReader("inputfiles/gotrains.txt", 'G');
		fileReader("inputfiles/streetcars.txt", 'X');
		fileReader("inputfiles/buses.txt", 'C');
		fileReader("inputfiles/gobuses.txt", 'D');
		
		//function write error log
		writeErrorLog();
		
		//function to determine hourly demands from passenger data
		determineDemand();
		
		//function to determine optimal fleet for each hour
		writeOptimalFleet();
		
		
		
	}
	
	
	static void fileReader(String path,  char type) {
		
		//create scanner to read in current file and use try catch block incase file cannot be found
		Scanner fromFile = null;
		
		try {
			fromFile = new Scanner(new FileInputStream(path));
		}
		catch (FileNotFoundException e) {	
			System.out.println("Error opening the file " + path + ". program aborted!");
			System.exit(0);
		}
		
		//declaring variables used in loop
		String fileLine;
		String[] splitLine = null;
		int lineCount = 0;

		//loop through each line of file
		while(fromFile.hasNextLine()) {
			lineCount++;
			fileLine = fromFile.nextLine();
			
			//splits the current line by the commas into an array so that each item of the array is seperated identifable data
			splitLine = fileLine.split(",");
			
			//use switch to create an object of type of file we are currently looking at
			switch(type) {
				case 'P' : {
					//using this function to determine if passenger object should be created or invalid data
					passErrorTest(splitLine, fileLine, lineCount);
					break;
				}
				case 'S' : {
					subways.add(new Subway( Integer.parseInt(splitLine[0]), 
											splitLine[1], 
											Integer.parseInt(splitLine[2]), 
											Integer.parseInt(splitLine[3]), 
											splitLine[4].charAt(0), 
											Integer.parseInt(splitLine[5]),
											fileLine));
					break;
				}
				case 'G' : {
					gotrains.add(new Gotrain(Integer.parseInt(splitLine[0]), 
											splitLine[1], 
											Integer.parseInt(splitLine[2]),
											fileLine));
					break;
				}
				case 'X' : {
					streetcars.add(new Streetcar(Integer.parseInt(splitLine[0]), 
												splitLine[1], 
												splitLine[2].charAt(0),
												fileLine));
					break;
				}
				case 'C' : {
					buses.add(new Bus(	Integer.parseInt(splitLine[0]), 
										splitLine[1], 
										Integer.parseInt(splitLine[2]),
										fileLine));
					break;
				}
				case 'D' : {
					gobuses.add(new Gobus(	Integer.parseInt(splitLine[0]), 
											splitLine[1], 
											Integer.parseInt(splitLine[2]),
											fileLine));
					break;
				}
			}
		}
		
		//close scanner
		fromFile.close();
		
	}
	
	
	static void passErrorTest(String[] splitLine, String currentLine, int lineNumber) {

		boolean errorFound = false;
		
		//use try catch block to detect if passenger object can be created. if not this line data is in an invalid form
		try {
			Passenger tempPass = new Passenger(
					splitLine[0],
					splitLine[1].charAt(0), 
					splitLine[2].charAt(0),
					Integer.parseInt(splitLine[3]),
					Integer.parseInt(splitLine[4])
					); //create a new passenger with current line
			
			//if passenger object succesfully made then make sure that each input is a valid input (example age can only b C, A, or S)
			if (splitLine[0].charAt(0) != '*' && splitLine[0].length() != 7 && splitLine[0].length() != 16) {
				passengerErrorLog.add("[Error: invalid id]" + "\n");
				errorFound = true;
			}
			if (splitLine[1].charAt(0) != 'S' && splitLine[1].charAt(0) != 'G' && splitLine[1].charAt(0) != 'X' && splitLine[1].charAt(0) != 'C' && splitLine[1].charAt(0) != 'D') {
				passengerErrorLog.add("[Error: invalid mode of transport]" + "\n");
				errorFound = true;
			}
			if (splitLine[2].charAt(0) != 'C' && splitLine[2].charAt(0) != 'A' && splitLine[2].charAt(0) != 'S') {
				passengerErrorLog.add("[Error: invalid age category]" + "\n");
				errorFound = true;
			}
			if (Integer.parseInt(splitLine[3]) < 1 || Integer.parseInt(splitLine[3]) > 24) {
				passengerErrorLog.add("[Error: invalid hour of day]" + "\n");
				errorFound = true;
			}
			else if (Integer.parseInt(splitLine[4]) != 20190304) {
				passengerErrorLog.add("[Error: not today's date]" + "\n");
				errorFound = true;
			}
			
			//if error was found, add to error log, if not add to list of passengers
			if (errorFound) {

				passengerErrorLog.add("[Line Number:" + lineNumber + "]" + "\n");
				passengerErrorLog.add( currentLine + "\n\n");
			}
			else {
				passengers.add(tempPass);
			}
		}
		catch(Exception e){
			passengerErrorLog.add("[Error: invalid line format]"  + "\n");
			passengerErrorLog.add("[Line Number:" + lineNumber + "]" + "\n");
			passengerErrorLog.add( currentLine + "\n\n");
			return;
		}
		
	}
	
	
	static void writeErrorLog() {
		
		//open file and to write and use try catch to catch file not found error
		PrintWriter toFile = null;
		
		try {
			toFile = new PrintWriter(new FileOutputStream("outputfiles/errorlog.txt"));
		}
		catch (FileNotFoundException e) {	
			System.out.println("Error opening the file 'errorlog.txt'. program aborted!");
			System.exit(0);
		}
		
		//loop through each item off error log array list and print each line
		for (String line: passengerErrorLog) {
			toFile.print(line);
		}
		
		//close file
		toFile.close();	
	}
	

	static void determineDemand() {
		
		//declare variable that holds seat increase determined by passengers age category
		double increase = 0;
		
		//loop through each passenger and assign their effective increase to their mode of transport during their hour of usage
		for (Passenger pass : passengers) {
			
			increase = pass.getIncrease();
			
			switch (pass.getModality()) {
				case 'S' : {
					subwayDemand[pass.getHour() - 1] += increase;
					break;
				}
				case 'G' : {
					gotrainDemand[pass.getHour() - 1] += increase;
					break;
				}
				case 'X' : {
					streetcarDemand[pass.getHour() - 1] += increase;
					break;
				}
				case 'C' : {
					busDemand[pass.getHour() - 1] += increase;
					break;
				}
				case 'D' : {
					gobusDemand[pass.getHour() - 1] += increase;
					break;
				}
			}
		}
	}

	
	static void writeOptimalFleet() {
		
		//open file and to write and use try catch to catch file not found error
		PrintWriter toFile = null;
		
		try {
			toFile = new PrintWriter(new FileOutputStream("outputfiles/inOperationFleets.txt"));
		}
		catch (FileNotFoundException e) {	
			System.out.println("Error opening the file 'inOperationFleets.txt'. program aborted!");
			System.exit(0);
		}
		
		//count variable to determine what type of vehicle we are one
		int count = 0;
		//loop through each type of vehicles hourly demand array
		for(double[] vehicleHourly: allHourly) {
			count++;
			
			//print hourly fleets depending on which type of vehicle currently on
			
			if (count == 1) {
				toFile.println("[Subway]");
				
				//loop through each hour
				for (int i = 1; i <=24; i++) {
					
					toFile.println("\n[Hour = " + i + "]");
					int sum = 0;
					int vehicleCount = 0;
					
					//print vehicles until capacity is greater then hourly demand
					for (Subway vehicle : subways) {
						
						if (sum >= vehicleHourly[i - 1]) {break;}
						if (vehicle.getOpStatus() != 'A') {continue;} //check if vehicle is avaliable
						sum += vehicle.getCapacity();
						vehicleCount++;
						toFile.println("subway: " + vehicle.getDescription());
					}
					
					toFile.println("[Count = " + vehicleCount + "]");
				}
			} 
			else if (count == 2) {
				
				toFile.println("\n[GoTrains]");
				
				//loop through each hour
				for (int i = 1; i <=24; i++) {
					
					toFile.println("\n[Hour = " + i + "]");
					int sum = 0;
					int vehicleCount = 0;
					
					//print vehicles until capacity is greater then hourly demand
					for (Gotrain vehicle : gotrains) {
						if (sum >= vehicleHourly[i - 1]) {break;}
						sum += vehicle.getCapacity();
						vehicleCount++;
						toFile.println("Gotrain: " + vehicle.getDescription());
					}
					
					toFile.println("[Count = " + vehicleCount + "]");
				}
			}
			
			else if (count == 3) {
				
				toFile.println("\n[Streetcars]");
				
				//loop through each hour
				for (int i = 1; i <=24; i++) {
					
					toFile.println("\n[Hour = " + i + "]");
					int sum = 0;
					int vehicleCount = 0;
					
					//print vehicles until capacity is greater then hourly demand
					for (Streetcar vehicle : streetcars) {
						
						if (sum >= vehicleHourly[i - 1]) {break;}
						sum += vehicle.getCapacity();
						vehicleCount++;
						toFile.println("Streetcar: " + vehicle.getDescription());
					}
					
					toFile.println("[Count = " + vehicleCount + "]");
				}
			}
				
			else if (count == 4) {
				
				toFile.println("\n[Buses]");
				
				//loop through each hour
				for (int i = 1; i <=24; i++) {
					
					toFile.println("\n[Hour = " + i + "]");
					
					int sum = 0;
					int vehicleCount = 0;
					
					//print vehicles until capacity is greater then hourly demand
					for (Bus vehicle : buses) {
						
						if (sum >= vehicleHourly[i - 1]) {break;}
						sum += vehicle.getCapacity();
						vehicleCount++;
						toFile.println("Bus: " + vehicle.getDescription());
					}
					
					toFile.println("[Count = " + vehicleCount + "]");
				}
			}
				
			else if (count == 5) {
				
				toFile.println("\n[Gobuses]"); 
				
				//loop through each hour
				for (int i = 1; i <=24; i++) {
					
					toFile.println("\n[Hour = " + i + "]");
					int sum = 0;
					int vehicleCount = 0;
					
					//print vehicles until capacity is greater then hourly demand
					for (Gobus vehicle : gobuses) {
						
						if (sum >= vehicleHourly[i - 1]) {break;}
						sum += vehicle.getCapacity();
						vehicleCount++;
						toFile.println("Gobus: " + vehicle.getDescription());
					}
					
					toFile.println("[Count = " + vehicleCount + "]");
				}
			}		
		}	
		//close file
		toFile.close();
	}
}
	
	
	



