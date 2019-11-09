/*------------------------------------------------------------------------
Author: 		Andrew Greenan
Student #:   	20004588
Description:  	Streetcar class is a child of the vehicle parent. It inherits
				all of the vehicles attributes and methods while also defining
				its own.
------------------------------------------------------------------------*/
package mtoptimizer;

public class Streetcar extends Vehicle {

	//streetcar also has a type attribute
	char type;
	
	public Streetcar(int unitParam, String idParam, char typeParam, String desParam) {
		super(unitParam, idParam, (typeParam == 'S') ? 40 : 80, desParam); //super sets attributes defined in parent class
		type	= typeParam;

	}
}
