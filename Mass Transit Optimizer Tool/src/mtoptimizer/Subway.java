/*------------------------------------------------------------------------
Author: 		Andrew Greenan
Student #:   	20004588
Description:  	Subway class is a child of the vehicle parent. It inherits
				all of the vehicles attributes and methods while also defining
				its own.
------------------------------------------------------------------------*/

package mtoptimizer;

public class Subway extends Vehicle {

	//additional attributes subways have
	int cars;
	int carCapacity;
	char opStatus;  //A = avail, U = Unavail, * is unknown
	int opStatusDate;
	
	public Subway(int unitParam, String idParam, int carsParam, int carCapacityParam, char opStatusParam, int opStatusDateParam, String desParam) {
		super(unitParam, idParam, (carsParam * carCapacityParam), desParam); //super sets attributes defined in parent class
		
		//set other attributes
		cars = carsParam;
		carCapacity = carCapacityParam;
		opStatus = opStatusParam;
		opStatusDate = opStatusDateParam;	
	}
	
	//subway has a attribute of whether operational or not
	public char getOpStatus() {
		return opStatus;
	}
}
