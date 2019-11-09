import java.util.Random;

public class Game {	
	
	public Game(Team homeTeam, Team awayTeam){

		//determining both teams skill through it total points and a method to determine if they play good or bad
		int totalSkillHome = homeTeam.getTotalPoints() + homeTeam.getrandomGoalie() + randomPlayWell();
		int totalSkillAway = awayTeam.getTotalPoints() + awayTeam.getrandomGoalie() + randomPlayWell();
		
		//determine how many goals each team score through goalSim method
		int homeGoals = goalSim(totalSkillHome);
		int awayGoals = goalSim(totalSkillAway);
		
		//utilize determineWin function to determine through simulated goals who is the winner
		determineWin(homeGoals, awayGoals, homeTeam, awayTeam);
		
	}

	//Method that uses Random numbers to simulate whether forwards, defence, and goalies play good or bad
	private int randomPlayWell() {
		
		int returnSkillChange = 0;
		
		//Randomly pick of forwards play poor (0) normal (1) well (2)
		Random rF = new Random();
		int randomIntF = rF.nextInt(3);
		
		if (randomIntF == 0) {
			returnSkillChange -= 25;
		}
		else if (randomIntF == 0) {
			returnSkillChange += 25;
		}
		
		
		//Randomly pick of defence play poor (0) normal (1) well (2)
		Random rD = new Random();
		int randomIntD = rD.nextInt(3);
				
		if (randomIntD == 0) {
			returnSkillChange -= 40;
		}
		else if (randomIntD == 0) {
			returnSkillChange += 40;
		}
		
		
		//Randomly pick of goalies play poor (0) normal (1) well (2)
		Random rG = new Random();
		int randomIntG = rG.nextInt(3);
						
		if (randomIntG == 0) {
			returnSkillChange -= 60;
		}
		else if (randomIntG == 0) {
			returnSkillChange += 60;
		}
		
		return returnSkillChange;
	}
	
	
	// using number of 50 points in a teams total skill to determine the amount of goals scored randomly
	private int goalSim(int skill) {
		
		int totalGoals = 0;
		
		//if there is a fraction of 50 left then randomly score 0 or 1 goals
		if ( skill % 50 != 0) {
			Random r = new Random();
			int randomInt = r.nextInt(2);
			
			totalGoals += randomInt;
		}
		
		
		//for each fifty points, a team can randomly score - 0-2 goals
		for(int x = 0; x < (skill / 50); x++) {
			Random r = new Random();
			int randomInt = r.nextInt(3);
			
			totalGoals += randomInt;
		}
		
		
		//return the total goals for the team requested
		return totalGoals;
	}
	
	
	//logic to determine if game must go to overtime or which team wins. then sends results to be store in each respective team object
	private void determineWin(int homeGoals, int awayGoals, Team homeTeam, Team awayTeam) {
		
		//if the goals are equal, the teams go to overtiem
		if (homeGoals == awayGoals) {
			int homeOvertimePoints = homeTeam.getOvertimePoints();
			int awayOvertimePoints = awayTeam.getOvertimePoints();
			
			//if the teams randomly generated overtime points are equal then winner is at random
			if (homeOvertimePoints == awayOvertimePoints ) {
				Random r = new Random();
				int randomInt = r.nextInt(2);
				
				if (randomInt == 0) {
					homeTeam.inputGameResult(true, false, (homeGoals + 1), awayGoals);
					awayTeam.inputGameResult(false, true, awayGoals, (homeGoals + 1));
				}
				else {
					homeTeam.inputGameResult(false, true, homeGoals, (awayGoals + 1));
					awayTeam.inputGameResult(true, false, (awayGoals + 1), homeGoals);
				}
			}
			
			//if not then it determines what team did win (has more ot points)
			else if (homeOvertimePoints > awayOvertimePoints ) {
				homeTeam.inputGameResult(true, false, (homeGoals + 1), awayGoals);
				awayTeam.inputGameResult(false, true, awayGoals, (homeGoals + 1));
			}
			else {
				homeTeam.inputGameResult(false, true, homeGoals, (awayGoals + 1));
				awayTeam.inputGameResult(true, false, (awayGoals + 1), homeGoals);
			}
		}
		
		//if it doesn't go to overtime then either home team or away team won
		else if (homeGoals > awayGoals){
			homeTeam.inputGameResult(true, false, homeGoals, awayGoals);
			awayTeam.inputGameResult(false, false, awayGoals, homeGoals);
		}
		else {
			homeTeam.inputGameResult(false, false, homeGoals, awayGoals);
			awayTeam.inputGameResult(true, false, awayGoals, homeGoals);
		}
	}
}
	