import java.util.Random;

public class Player {

	int playerNumber;
	String playerName;
	String playerPosition;
	int playerRating;
	
	//constructor is used to assign player variables from inputted data from the Team object
	public Player (int min, int max, String position, int playerNumberGenerated, boolean leafs, String leafsName, int leafsSkill) {
		
		//Applying player Number
		playerNumber = playerNumberGenerated;
		
		//Applying player Position
		playerPosition = position;
		
		
		//Applying player name. If it is Toronto team use there name 
		if (leafs) {
			playerName = leafsName;
			playerRating = leafsSkill;
		}
		
		//if other team generate name from position and number
		else {
			if (position == "forward") {
				playerName = ("Fn" + Integer.toString(playerNumber));
			}
			else if (position == "defence") {
				playerName = ("Dn" + Integer.toString(playerNumber));
			}
			else if (position == "goalie") {
				playerName = ("Gn" + Integer.toString(playerNumber));
			}
			
			//if other team, also randomly generate skills between min and max skill levels
			Random r = new Random();;
			int randomInt = r.nextInt((max + 1) - min ) + min;
			
			playerRating = randomInt;
		}
	
			
	}
	
	
	//the following get methods are used to provide variable information of an object to a different class requesting it
	public int getNumber() {
		return playerNumber;
	}
	
	public String getName() {
		return playerName;
	}
	
	public int getSkillLevel() {
		return playerRating;
	}
	
}
