package application;

import java.util.Scanner;

public class Utility extends Property{
	
	
	/*
	 * TODO: methods for rent as the act differently than a normal property. 
	 */
	
	
	/**
	 * 
	 * @param position
	 * @param color
	 * @param name
	 * @param price
	 * @param mortgage
	 * @param rent
	 * Constructor for utility properties (including railroads)
	 */
	
	
	public Utility(String inputLine) {
		super(inputLine);
		
		Scanner lnScn = new Scanner(inputLine);
		lnScn.useDelimiter(",");
		
		
		
		lnScn.next();
		lnScn.next();			//These three are taken care of in super
		lnScn.next();
		
		lnScn.next(); 			//Skip over Tax space
		
		lnScn.nextInt();		//These two are taken care of in super
		lnScn.nextInt();
		
		
		lnScn.nextInt();		//no rent cost
		this.rent = lnScn.nextInt();
		
		
		
		
		lnScn.close();
		
	}
	/**
	 * set the rent of the utility
	 */
	public int calcRent(String type, int diceRoll, int numTypeOwned) {
		 
		if(type.equals(anObject))
		
		
		return rent;
	}
	
	
}
