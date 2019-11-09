/*------------------------------------------------------------------------
Author: 		Andrew Greenan
Student #:   	20004588
Description:  	Bus class is a child of the vehicle parent. It inherits
				all of the vehicles attributes and methods while also defining
				its own.
------------------------------------------------------------------------*/
package mtoptimizer;

public class Bus extends Vehicle {

	public Bus(int unitParam, String idParam, int capParam, String desParam) {
		super(unitParam, idParam, capParam, desParam); //super sets attributes defined in parent class
	}
}
