package application;

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
	
	
	public Utility(int location, String color, String name, int value, int mortgageValue, int rent, boolean isMortgaged, boolean isBought) {
		super(location, color, name, value, mortgageValue, rent, isMortgaged, isBought);
		
	}
	/**
	 * set the rent of the utility
	 */
	public void setRent() {
		//this changes depending on how many are owned and which kind of utility. 
	}
	
	
}
