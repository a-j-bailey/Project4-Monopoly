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
		

	}
	/**
	 * set the rent of the utility
	 */

	public int calcUtilityRent(int diceRoll, int numTypeOwned) {


		if (numTypeOwned == 2) {
			return diceRoll * 10;
		}
		else {
			return diceRoll * 4;
		}


	}

	public int calcRailroadRent (int numTypeOwned) {
		if(numTypeOwned == 1) {
			return 25;
		}
		if(numTypeOwned == 2) {
			return 50;
		}
		if(numTypeOwned == 3) {
			return 100;
		}
		else {
			return 200;
		}
	}
}
