/*------------------------------------------------------------------------
Author: 		Andrew Greenan
Student #:   	20004588
Description:  	Passenger class is currently a stand alone class that is used
				to create passenger objects for each passenger line of data.
				it has attributes describing the passenger and methods to get
				these attributes. With a larger scale application, I can see
				this class being a parent other children classes that describe
				different types of passengers
------------------------------------------------------------------------*/

package mtoptimizer;

public class Passenger {
	
	//attributes
	String id;
	char modality;
	char age; 
	int hour;
	int date;
	
	//constructor adds attributes
	public Passenger(String idParam, char modeParam, char ageParam, int hourParam, int dateParam) {
	
		//assigning attributes
		id 			= idParam;
		modality 	= modeParam;
		age 		= ageParam;
		hour 		= hourParam;
		date 		= dateParam;
	}
	
	//get form of transport
	public char getModality() {
		return modality;
	}
	
	//getting amount to increase seat demand due to age
	public double getIncrease() {
		if (age == 'A') 		{return 1.0;}
		else if (age == 'C') 	{return 0.75;}
		else 					{return 1.5;}
	}
	
	//get hour that this customer traveled in
	public int getHour() {
		return hour;
	}
	
}
