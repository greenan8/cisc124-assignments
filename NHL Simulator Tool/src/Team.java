import java.util.Random;

public class Team {

	//team name variable
	String name;

	//team stats variables
	int gp = 0;
	int win = 0;
	int loss = 0;
	int otl = 0;
	int points = 0;
	int gf = 0;
	int ga = 0;
	int diff = 0;
	
	//team player object array
	Player[] forwards = new Player[13];
	Player[] defencemen = new Player[8];
	Player[] goalies = new Player[4];
	
	//variable to hold the points/skills total of roster used by game class
	int playerTotalPoints;
	
	//constructor method will be used to create team and roster
	public Team(String nameInput, boolean leafsTeam, 
			int forwardMin, int forwardMax, 
			int defenceMin, int defenceMax,
			int goalieMin, int goalieMax
			) {
		
		//name is added
		name = nameInput;
		
		
		//if leafs create all leafs players from data set, if not, randomly generate players
		if (leafsTeam == true) {
			addLeafsRoster();
		}
		else {
			createRandomRoster(forwardMin, forwardMax, defenceMin, defenceMax, goalieMin, goalieMax);
		}	
		
		//calculate the teams skill total
		calculateRosterSkillTotal();
		
	}
	
	//this method creates the random team roster
	private void createRandomRoster(int fMin, int fMax, int dMin, int dMax, int gMin, int gMax ) {
		
		//create array for the random player numbers
		int[] randomN = randomPlayerNumberGen();
		
		//Create Forwards
		for (int x = 0; x < 13; x++) {
			
			//create player objects
			Player newForward = new Player(fMin, fMax, "forward", randomN[x], false, null, 0);
			
			//add to forward array for this team
			forwards[x] = newForward;
		}
		
		//Create Defence
		for (int x = 13; x < 21; x++) {
			
			//create player objects
			Player newDefence = new Player(dMin, dMax, "defence", randomN[x], false, null, 0);
			
			//add to defencemen array for this team
			defencemen[x-13] = newDefence;
		}
		
		//Create Goalies
		for (int x = 21; x < 25; x++) {
			
			//create player objects
			Player newGoalie = new Player(gMin, gMax, "goalie", randomN[x], false, null, 0);
			
			//add to defencemen array for this team
			goalies[x-21] = newGoalie;
		}
	}
	
	//this method is used to create the toronto roster from given data
	private void addLeafsRoster() {
		//inputting all given data to create player objects for Toronto Object
		forwards[0] = new Player (0 , 0, "forward", 28, true, "C. Brown", 5);
		forwards[1] = new Player (0 , 0, "forward", 63, true, "T. Ennis", 4);
		forwards[2] = new Player (0 , 0, "forward", 33, true, "F. Gauthier", 5);
		forwards[3] = new Player (0 , 0, "forward", 11, true, "Z. Hyman", 7);
		forwards[4] = new Player (0 , 0, "forward", 18, true, "A. Johnsson", 7);
		forwards[5] = new Player (0 , 0, "forward", 43, true, "N. Kadri", 7);
		forwards[6] = new Player (0 , 0, "forward", 24, true, "K. Kapanen", 8);
		forwards[7] = new Player (0 , 0, "forward", 26, true, "P. Lindholm", 8);
		forwards[8] = new Player (0 , 0, "forward", 12, true, "P. Marleau", 8);
		forwards[9] = new Player (0 , 0, "forward", 16, true, "M. Marner", 9);
		forwards[10] = new Player (0 , 0, "forward", 34, true, "A. Matthews", 9);
		forwards[11] = new Player (0 , 0, "forward", 29, true, "W. Nylander", 9);
		forwards[12] = new Player (0 , 0, "forward", 91, true, "J.Tavares", 10);

		defencemen[0] = new Player (0 , 0, "defence", 23, true, "T. Dermott", 8);
		defencemen[1] = new Player (0 , 0, "defence", 51, true, "J. Gardiner", 4);
		defencemen[2] = new Player (0 , 0, "defence", 2, true, "R. Hainsey", 5);
		defencemen[3] = new Player (0 , 0, "defence", 3, true, "J. Holl", 6);
		defencemen[4] = new Player (0 , 0, "defence", 52, true, "M. Marincin", 4);
		defencemen[5] = new Player (0 , 0, "defence", 92, true, "I. Ozhiganov", 6);
		defencemen[6] = new Player (0 , 0, "defence", 44, true, "M. Rielly", 9);
		defencemen[7] = new Player (0 , 0, "defence", 22, true, "N. Zaitsev", 8);
		
		//I am sorry but Freddie is not a 10 lol, u where pushing with Nylander as a 9
		goalies[0] = new Player (0 , 0, "goalie", 31, true, "F.Andersen", 8);
		goalies[1] = new Player (0 , 0, "goalie", 30, true, "M. Hutchinson", 7);
		goalies[2] = new Player (0 , 0, "goalie", 50, true, "K. Kaskisuo", 5);
		goalies[3] = new Player (0 , 0, "goalie", 40, true, "G. Sparks", 6);
	}
	
	//this method calculates the total skill of them team from summing each players skill
	private void calculateRosterSkillTotal() {
 				for (Player fSkater : forwards) {
 					playerTotalPoints += fSkater.getSkillLevel();
 				}
 				for (Player dSkater : defencemen) {
 					playerTotalPoints += dSkater.getSkillLevel();
 				}
 	}
 	
 	//this method is used to generate the 25 random numbers to assign to the generated players
	public int[] randomPlayerNumberGen()  {
		
		//logic is taken from lesson in class
		Random generator = new Random(System.currentTimeMillis());
		
		int nextRandomNumber;		// Stores next random integer
		int lowerBound = 1;			// Minimum random integer
		int range = 99;				// Range of random integers
		int maxCount = 25;			// Maximum number of random integers
		
		
		// Array to hold "maxCount" integers.
		int[] returnArray = new int[maxCount];
		
		int count =0;
		int i = 0;
		
		// First random integer generated cannot be a duplicate.
		returnArray[0] = lowerBound + generator.nextInt(range);
		
		// Generate the remaining 25 random integers.
		while (count < maxCount) {
			nextRandomNumber = lowerBound + generator.nextInt(range);
			for (i = 0; i < count; i++)
				if (returnArray[i] == nextRandomNumber)
					break;
	
			if (i == count && returnArray[i] != nextRandomNumber) {
				count += 1;
				if (count < maxCount) {
				returnArray[count] = nextRandomNumber;
				}
			}	
		}
		return returnArray;
	}

	//This method is used by the Game class to input results of the Team objects game and update/store it in the object
	public void inputGameResult(boolean winInput, boolean otLoss, int gfInput, int gaInput) {
		
		//inputting win otl or loss from a game --- and updating points
		if (winInput) {
			win += 1;
			points += 2;
		}
		else if (otLoss) {
			otl += 1;
			points += 1;
		}
		else {
			loss += 1;
		}
		
		//updating goal stats
		gf += gfInput;
		ga += gaInput;
		diff = gf - ga;
		
		//update games played
		gp += 1;
	}

 	//the following print methods are use from the NHLSimulator class to print info requested about Team objects from the user
	
	public void printPlayers() {
		//print forwards in the table format outlined
		for (Player skater : forwards) {	
			System.out.printf(
					"%-15d%-15s%-15s%-15d%n",
					skater.getNumber(), skater.getName(), "Forward", skater.getSkillLevel());
			
		}
		
		//print defence in the table format outlined
		for (Player skater : defencemen) {
			System.out.printf(
					"%-15d%-15s%-15s%-15d%n",
					skater.getNumber(), skater.getName(), "Defence", skater.getSkillLevel());
			}
		
		//print goalies in the table format outlined
		for (Player skater : goalies) {
			System.out.printf(
					"%-15d%-15s%-15s%-15d%n",
					skater.getNumber(), skater.getName(), "Goalie", skater.getSkillLevel());
		}
	}

	public void printStats() {
		//print stats in the table format given
		System.out.printf(
							"%-16s%10d%10d%10d%10d%10d%10d%10d%10d%n",
							name, gp , win, loss, otl, points, gf, ga, diff);	
	}

	public void printPoints() {
		//print points after season sim
				System.out.printf("%-16s%10s%-4d%n",name, "Points:", points);			
	}

	//the following get methods are used to provide variable information of an object to a different class requesting it
	
	public String getTeamName() {
		return name;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getTotalPoints() {
		return playerTotalPoints;
	}
	
	public int getrandomGoalie() {
		//random number between 0 and 3 to determine one of the 4 goalies to play
		Random r = new Random();;
		int randomIntGoalie = r.nextInt(4);
		return goalies[randomIntGoalie].getSkillLevel();
	}
	
	public int getOvertimePoints() {
	
		int overtimePoints = 0;
		
		//randomly select two forwards to play overtime
		for (int x = 0; x < 2; x++) {
			Random r1 = new Random();
			int randomIntForward = r1.nextInt(13);
			overtimePoints += forwards[randomIntForward].getSkillLevel();
		}
		
		//randomly select one defence to play overtime	
		Random r2 = new Random();
		int randomIntForward = r2.nextInt(8);
		overtimePoints += defencemen[randomIntForward].getSkillLevel();
				
		//randomly select one goalie to play overtime
		Random r3 = new Random();;
		int randomIntGoalie = r3.nextInt(4);
		overtimePoints += goalies[randomIntGoalie].getSkillLevel();
		
		return overtimePoints;
	}
	
}

