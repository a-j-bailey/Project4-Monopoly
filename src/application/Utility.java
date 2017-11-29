package application;


public class Utility extends Property{
	
	
	
	
	
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
			return diceRoll * 4;				//If only one is owned do this
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
		else {								//If four are owned
			return 200;
		}
	}
}
