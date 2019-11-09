/*------------------------------------------------------------------------
Author: 		Andrew Greenan
Student #:   	20004588
Description:  	Vehicle class is the parent to all other vehicles it has
				all common attributes that generic vehicle would have
				and also has shared methods that would be needed by any
				child class.
------------------------------------------------------------------------*/


package mtoptimizer;

public class Vehicle {
	
	//attributes of a vehicle
	int unitNum;
	String id;
	int capacity;
	String description;
	
	//constructor class sets attributes
	public Vehicle(int unitParam, String idParam, int capParam, String desParam) {
		
		//assigning attributes
		unitNum 	= unitParam;
		id 			= idParam;
		capacity 	= capParam;
		description	= desParam;
	}
	
	//method to provide capacity. Can be used by a vehicle object or a child class of vehicle object
	public int getCapacity() {
		return capacity;
	}
	
	//method to provide description. Can be used by a vehicle object or a child class of vehicle object
	public String getDescription() {
		return description;
	}
}
