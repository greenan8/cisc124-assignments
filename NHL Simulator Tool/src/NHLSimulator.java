/*--------------------------------------------------------------*/
// Author: 		Andrew Greenan
// Student #:   20004588
// Description: This program simulates the nhl season for the Toronto
// 				Maple leafs through generating random team roster with a range of skill
//				and using a given algoritham to determine the outcome of games
// 				played between each team in the eastern conference.
//				
//				NOTE: did not put in any seed for random number generations
//				as this seemed to have the best results with normal standing.
//				Other seeds would cause extremely high overtime games played.
/*--------------------------------------------------------------*/

import java.util.*;

public class NHLSimulator {
	
	static Team[] nhlEastTeams = new Team[16];
	static boolean seasonSim = false;
	
	//the main method calls both the teamBuilder method to create the eastern conf teams
	//and also calls the mainMenu method to display main menu to user
	public static void main(String[] args) {
		teamBuilder();
		mainMenu();
	}
	
	//main menu displays options for users
	private static void mainMenu() {
		System.out.println(
				"\n NHL Simulator (Version 0.1). Author: Andrew Greenan \n"
				+ "1 – Simulate NHL Season (Eastern Conference) \n"
				+ "2 – View Team Skill Level Profile \n"
				+ "3 – Display End of Regular Season Table \n"
				+ "Select Option [1, 2, or 3] (9 to Quit):"
				);
		
		//uses input method to start scanning for input and also process input received
		optionInput(null);
	}
	
	//this method is use to read the input from a user after receiving the main menu
	private static void optionInput(String errorMessage) {
		
		//if there is an error message passed, display it before input option
		if (errorMessage != null) {
			System.out.println(errorMessage);
		}
		
		//create scanner to recieve input
		Scanner inputScanner = new Scanner(System.in);
		String response = inputScanner.nextLine();
		
		//determine what user inputted and what the application should do
		if (response.equals("1")) {
			if (seasonSim == false) {
				seasonSimulate();
			}
			else {
				optionInput("Error: you have already simulated the season. try another option:");
			}
		}
		else if (response.equals("2")) {
			System.out.println("Enter the Team name you would like to view or press enter to return:");
			teamSkillProfile();
		}
		else if (response.equals("3")) {
			printSeasonResults();
		}
		else if (response.equals("9")) {
			System.out.println("Simulation Shutting Down...");
			System.exit(0);
		}
		else {
			optionInput("Try inputting a number from the menu:");
		}
		
	}
	
	//this method creates all 16 team objects
	private static void teamBuilder() {
		
		Team boston = new Team("Boston", false, 5, 9, 4, 9, 5, 7);
		nhlEastTeams[0] = boston;
		Team buffalo = new Team("Buffalo", false, 6, 9, 4, 7, 4, 7);
		nhlEastTeams[1] = buffalo;
		Team carolina = new Team("Carolina", false, 4, 8, 5, 7, 4, 9);
		nhlEastTeams[2] = carolina;
		Team columbus = new Team("Columbus", false, 4, 9, 5, 8, 7, 10);
		nhlEastTeams[3] = columbus;
		Team detroit = new Team("Detroit", false, 4, 7, 6, 8, 4, 6);
		nhlEastTeams[4] = detroit;
		Team florida = new Team("Florida", false, 5, 7, 4, 8, 5, 9);
		nhlEastTeams[5] = florida;
		
		/* adjusted Montreals numbers cuz they are way better then the leafs fan
		 * who wrote this assignment had them rated at :P
		 */
		Team montreal = new Team("Montreal", false, 5, 8, 5, 9, 6, 10);
		nhlEastTeams[6] = montreal;
		Team newJersey = new Team("New Jersey", false, 4, 7, 4, 7, 5, 6);
		nhlEastTeams[7] = newJersey;
		Team nyIslanders = new Team("NY Islanders", false, 6, 8, 5, 7, 6, 8);
		nhlEastTeams[8] = nyIslanders;
		Team nyRangers = new Team("NY Rangers", false, 5, 7, 4, 6, 5, 7);
		nhlEastTeams[9] = nyRangers;
		Team ottawa = new Team("Ottawa", false, 4, 6, 4, 5, 4, 5);
		nhlEastTeams[10] = ottawa;
		Team philadelphia = new Team("Philadelphia", false, 4, 6, 4, 6, 4, 7);
		nhlEastTeams[11] = philadelphia;
		Team pittsburgh = new Team("Pittsburgh", false, 6, 10, 4, 7, 5, 7);
		nhlEastTeams[12] = pittsburgh;
		Team tampaBay = new Team("Tampa Bay", false, 6, 10, 6, 10, 7, 9);
		nhlEastTeams[13] = tampaBay;
		Team toronto = new Team("Toronto", true, 0, 0, 0, 0, 0, 0);
		nhlEastTeams[14] = toronto;
		Team washington = new Team("Washington", false, 6, 10, 5, 8, 6, 8);
		nhlEastTeams[15] = washington;	
	}

	//this method displays the requested team players
	private static void teamSkillProfile() {
		
		//scan for user input of a team name
		Scanner skillScanner = new Scanner(System.in);
		String response = skillScanner.nextLine();
		boolean success = false;
		
		//send back to main menu if enter is inputed
		if (response.equals("")) {
			mainMenu();	
			skillScanner.close();
		}
		//if user did not press enter then search to see if input matches a team name and print
		else {
			for (Team team : nhlEastTeams) {
				if (response.equals(team.getTeamName())) {
					success = true;
					System.out.printf(
									"%-15s%-15s%-15s%-15s%n",
									"No.", "Name", "Position", "Skill level");
					System.out.println("===========================================================");
		
					
					team.printPlayers();
					break;
				}	
			}
		}
		//if the input does not match a team, ask to input again
		if (!success) {
			System.out.println(response +" could not be found, try again or hit enter to return:");
			teamSkillProfile();
		}
		//if succesful in finding a team prompt to search again or go back
		else {
			System.out.println("Feel free to enter another team or press enter to return:");
			teamSkillProfile();
		}		
	}

	//this prints every team in alphebatical order displaying stats of the team in a table
	private static void printSeasonResults() {
		//first check if season has been sim'd
		if (seasonSim) {
			
			//print header
			System.out.printf(
					"%-16s%10s%10s%10s%10s%10s%10s%10s%10s%n",
					"Team Name", "GP", "W", "L", "OTL", "PTS", "GF", "GA", "DFF");
			System.out.println("==================================================================================================");
			
			//print stats for every team object
			for (Team team : nhlEastTeams) {
				team.printStats();
			}
			
			//prompt hitting any key to go back to menu
			pressAnyKeyToContinue();
		}
		//if season hasn't sim'd yet send error
		else {
			optionInput("Error: You must first sim the season to view this request");
		}
	}
	
	//this method calls the game method between all teams twice to sim season
	private static void seasonSimulate() {
		
		//sim two game played between every team (home and away) use simple loop algo
		for (int x = 0; x < nhlEastTeams.length; x++) {
			for (int y = (x+1); y < nhlEastTeams.length; y++) {
				new Game(nhlEastTeams[x], nhlEastTeams[y]);
				new Game(nhlEastTeams[y], nhlEastTeams[x]);
			}
		}
		//set seasonSim to true so the program know sim has occoured and also print results
		seasonSim = true;
		printTeamPoints();
		mainMenu();
	}
	
	//this method is use when any key to continue back to main menu is needed
	private static void pressAnyKeyToContinue(){ 
	        
		//detect if a key is pressed
		System.out.println("Press enter key to continue...");
		try
	    {
	    	System.in.read();
	    	mainMenu();
	    	}  
	        catch(Exception e){}  
	 }
	
	//this method prints each teams points in order after the season sims
	private static void printTeamPoints() {
if (seasonSim) {
			
			System.out.println("NHL Regular Season – Eastern Conference – 2018/2019");
			
			//create team arrays we can manipulate
			Team[] standingsLoopArray = nhlEastTeams;
			Team[] standingsPlaceholder = nhlEastTeams;
			
			//we must loop for the every team in the east
			for (int x = 16; x > 0; x--) {
				standingsLoopArray = standingsPlaceholder;
				Team highestTeam = null;
				
				//this loop inside the first determines which team has the highest points
				for (int i = 0; i < x; i++) {
					if (highestTeam != null) {
						if (standingsLoopArray[i].getPoints() > highestTeam.getPoints() ) {
							highestTeam = standingsLoopArray[i];
						}	
					}
					//in first loop must set highestTeam to first team looked at
					else {
						highestTeam = standingsLoopArray[i];
					}		
				}
					//print out the team determined to have highest points in current array
					highestTeam.printPoints();
				
					//this loop is used to then recreate the array with one less team then last time
					//that is removing the team that was just printed
					standingsPlaceholder = new Team[x];
					for(int i = 0; i < x; i++){
						if(standingsLoopArray[i] == highestTeam){
			               // shifting elements
			                for(int j = i; j < x - 1; j++){
			                    standingsPlaceholder[j] = standingsLoopArray[j+1];
			                }
			                break;
			            }
						
						standingsPlaceholder[i] = standingsLoopArray[i];
			        }
				
			}
			
			//if the habs are higher then the leafs, rub it in!
			if (nhlEastTeams[6].getPoints() > nhlEastTeams[14].getPoints()) {
				System.out.println("\n HAHA Leafs suck. GO HABS GO!");
			}
			System.out.println("\n Season has been simulated...");
			mainMenu();
		}
		else {
			optionInput("Error: You must first sim the season to view this request");
		}
	}
}
	
	

	
	